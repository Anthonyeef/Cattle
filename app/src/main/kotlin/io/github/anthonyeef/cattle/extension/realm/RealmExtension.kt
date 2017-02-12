package io.github.anthonyeef.cattle.extension.realm

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery

/**
 * Utility extension for modifying database. Create a transaction, run the function passed as argument,
 * commit transaction and close realm instance.
 */
fun Realm.transaction(action: (Realm) -> Unit) {
    use { executeTransaction { action(this) } }
}

/**
 * Creates a new entry in database or updates an existing one. If entity has no primary key, always create a new one.
 * If has primary key, it tries to updates an existing one.
 */
fun <T : RealmObject> T.save() {
    Realm.getDefaultInstance().transaction {
        if(this.hasPrimaryKey(it)) it.copyToRealmOrUpdate(this) else it.copyToRealm(this)
    }
}

/**
 * Creates or updates a entry in database. Requires a RealmObject with primary key, or IllegalArgumentException will be thrown
 */
fun <T : RealmObject> T.createOrUpdate() {
    Realm.getDefaultInstance().transaction { it.copyToRealmOrUpdate(this) }
}

/**
 * Query to the database with RealmQuery instance as argument
 */
fun <T : RealmObject> T.query(query: (RealmQuery<T>) -> Unit): List<T> {

    Realm.getDefaultInstance().use { realm ->
        val result = realm.forEntity(this).withQuery(query).findAll()
        return realm.copyFromRealm(result)
    }
}

private fun <T : RealmObject> Realm.forEntity(instance : T) : RealmQuery<T>{
    return RealmQuery.createQuery(this, instance.javaClass)
}

private fun <T> T.withQuery(block: (T) -> Unit): T { block(this); return this }

private fun <T : RealmObject> T.hasPrimaryKey(realm : Realm) = realm.schema.get(this.javaClass.simpleName).hasPrimaryKey()