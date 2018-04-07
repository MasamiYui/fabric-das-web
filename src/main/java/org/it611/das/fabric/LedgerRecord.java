package org.it611.das.fabric;

/**
 * 账本数据结构
 */
public class LedgerRecord {
    private String Key;
    private String Value;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public LedgerRecord(String key, String value) {
        Key = key;
        Value = value;
    }

    public String[] toStringArray() {
        return new String[] {this.getKey(),this.getValue()};
    }

    @Override
    public String toString() {
        return "LedgerRecord{" +
                "Key='" + Key + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
