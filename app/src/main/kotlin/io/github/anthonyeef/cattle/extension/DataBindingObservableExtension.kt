package io.github.anthonyeef.cattle.extension

import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import io.reactivex.Observable
import io.reactivex.Observable.create
import androidx.databinding.Observable as DataBindingObservable

/**
 * Created by wuyifen on 04/03/2018.
 */
@Suppress("UNCHECKED_CAST")
private inline fun <T : DataBindingObservable, R : Any?> T.observe(
        crossinline block: (T) -> R
): Observable<R> = create { subscriber ->
    object : OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: DataBindingObservable, id: Int) = try {
            subscriber.onNext(block(observable as T))
        } catch (e: Exception) {
            subscriber.onError(e)
        }
    }.let {
        subscriber.setCancellable { this.removeOnPropertyChangedCallback(it) }
        this.addOnPropertyChangedCallback(it)
    }
}

class ObservableString(initialValue: String) : ObservableField<String>(initialValue)

fun ObservableInt.observe() = observe { it.get() }
fun ObservableString.observe() = observe { it.get() }

fun <T : Any> ObservableField<T>.observe() = observe { it.get() }