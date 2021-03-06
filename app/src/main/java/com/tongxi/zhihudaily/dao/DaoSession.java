package com.tongxi.zhihudaily.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.tongxi.zhihudaily.model.MyCollectionBean;

import com.tongxi.zhihudaily.dao.MyCollectionBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig myCollectionBeanDaoConfig;

    private final MyCollectionBeanDao myCollectionBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        myCollectionBeanDaoConfig = daoConfigMap.get(MyCollectionBeanDao.class).clone();
        myCollectionBeanDaoConfig.initIdentityScope(type);

        myCollectionBeanDao = new MyCollectionBeanDao(myCollectionBeanDaoConfig, this);

        registerDao(MyCollectionBean.class, myCollectionBeanDao);
    }
    
    public void clear() {
        myCollectionBeanDaoConfig.clearIdentityScope();
    }

    public MyCollectionBeanDao getMyCollectionBeanDao() {
        return myCollectionBeanDao;
    }

}
