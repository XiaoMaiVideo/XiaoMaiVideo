/**
 * author: 何慷
 * createTime：2020/7/17
 */
package com.edu.whu.xiaomaivideo.model;


import java.util.List;


public class CommentEntity {

    public static final int TYPE_COMMENT_PARENT = 1;
    public static final int TYPE_COMMENT_CHILD = 2;
    public static final int TYPE_COMMENT_MORE = 3;
    public static final int TYPE_COMMENT_EMPTY = 4;
    public static final int TYPE_COMMENT_OTHER = 5;

    private List<FirstLevelComment> firstLevelComments;
    private long totalCount;

    public List<FirstLevelComment> getFirstLevelComments() {
        return firstLevelComments;
    }

    public void setFirstLevelComments(List<FirstLevelComment> firstLevelComments) {
        this.firstLevelComments = firstLevelComments;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"firstLevelComment\":")
                .append(firstLevelComments);
        sb.append(",\"totalCount\":")
                .append(totalCount);
        sb.append('}');
        return sb.toString();
    }
}
