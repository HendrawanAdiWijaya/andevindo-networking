package com.andevindo.andevindonetworking;

import java.util.List;

/**
 * Created by heendher on 11/11/2016.
 */

public interface BaseNetworkListener {

    interface BaseList{
        void onNull();
        void onNetworkError();
        void onServerError();
    }

    interface BaseSimple{
        void onFailed(String message);
    }

    interface SimpleListener extends BaseSimple{
        void onSuccess();
    }

    interface SimpleOneParamaterListener<T> extends BaseSimple{
        void onSuccess(T t);
    }

    interface ListListener<T> extends BaseList{
        void onSuccess(List<T> list);
    }

    interface ListAddMoreListener<T> extends ListListener<T>{
        void onMoreSuccess(List<T> list);
        void onMoreNull();
        void onMoreNetworkError();
        void onMoreServerError();
    }

    interface Secure{
        void onUnauthenticated();
    }

}
