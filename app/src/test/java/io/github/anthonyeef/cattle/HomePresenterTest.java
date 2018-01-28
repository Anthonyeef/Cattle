package io.github.anthonyeef.cattle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.github.anthonyeef.cattle.contract.HomeActivityContract;
import io.github.anthonyeef.cattle.presenter.HomeActivityPresenter;

/**
 *
 */
public class HomePresenterTest {

    @Mock
    private HomeActivityContract.View mHomeView;

    private HomeActivityPresenter mHomeActivityPresenter;

    @Before
    public void setupHomePresenter() {
        MockitoAnnotations.initMocks(this);
        mHomeActivityPresenter = new HomeActivityPresenter(mHomeView);
    }

    @Test
    public void clickComposeBtnAndOpenComposeActivity() {
        mHomeActivityPresenter.composeNewFanfou();
        Mockito.verify(mHomeView).showComposeActivity();
    }
}
