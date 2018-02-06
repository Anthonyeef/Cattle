package io.github.anthonyeef.cattle;

import android.support.test.espresso.core.deps.guava.collect.Lists;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.github.anthonyeef.cattle.contract.ProfileAlbumContract;
import io.github.anthonyeef.cattle.data.source.album.AlbumRepository;
import io.github.anthonyeef.cattle.data.statusData.Status;
import io.github.anthonyeef.cattle.presenter.ProfileAlbumPresenter;
import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class ProfileAlbumPresenterTest {

    private List<Status> STATUS;

    @Mock
    private AlbumRepository mAlbumRepository;

    @Mock
    private ProfileAlbumContract.View mProfileAlbumView;


    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    private ProfileAlbumPresenter mProfileAlbumPresenter;

    private String testUid = "test_uid";

    @Before
    public void setupProfileGalleryPresenter() {
        MockitoAnnotations.initMocks(this);
        mProfileAlbumPresenter = new ProfileAlbumPresenter(mProfileAlbumView, mAlbumRepository, testUid);
        STATUS = Lists.newArrayList(new Status(111, "lalala", "test", "source", "createAt", false, false, "",
                        "", "", "", "", "", "", null, null, null, true),
                new Status(112, "lalalala", "test1", "source", "createAt", false, false, "",
                        "", "", "", "", "", "", null, null, null, true));
    }


    @Test
    public void createPresenter_setsThePresenterToView() {
        verify(mProfileAlbumView).setPresenter(mProfileAlbumPresenter);
    }

    @Test
    public void populatePhotos_callViewShowPhotos() {
        when(mAlbumRepository.getAlbumPhotos(testUid, "")).thenReturn(Flowable.just(STATUS));
        mProfileAlbumPresenter.loadPhotos();
        verify(mProfileAlbumView).showPhotoStatus(true, STATUS);
    }

    @Test
    public void emptyPhotos_callViewShowEmptyHint() {
        when(mAlbumRepository.getAlbumPhotos(testUid, "")).thenReturn(Flowable.just(Collections.emptyList()));
        mProfileAlbumPresenter.loadPhotos();
        verify(mProfileAlbumView).showEmptyView();
    }
}
