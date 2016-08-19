package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * 会员分类
 */
public class MemberClassify  implements Serializable {

    int   typeId;
    String typeName;
    String dataSize;
    String typeDiscountRatio;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getTypeDiscountRatio() {
        return typeDiscountRatio;
    }

    public void setTypeDiscountRatio(String typeDiscountRatio) {
        this.typeDiscountRatio = typeDiscountRatio;
    }
}
