package com.example.activitytimer;

import java.io.Serializable;

class Activity implements Serializable {
    public static final long serialVersionUID = 20220912L;

    private long m_Id;
    private final String mName;
    private final String mDescription;
    private final int mSortOrder;

    public Activity(long id, String name, String description, int sort_order) {
        this.m_Id = id;
        this.mName = name;
        this.mDescription = description;
        this.mSortOrder = sort_order;
    }

    public long getId() {
        return m_Id;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getSortOrder() {
        return mSortOrder;
    }

    public void setId(long id) {
        this.m_Id = id;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "m_Id=" + m_Id +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
