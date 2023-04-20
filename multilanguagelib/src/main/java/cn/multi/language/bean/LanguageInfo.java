package cn.multi.language.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LanguageInfo implements Serializable {

    String local;   // 语言标识
    String version; // 语言版本
    // 语言key-value对
    List<StringPair> list = new ArrayList<>();

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<StringPair> getList() {
        return list;
    }

    public void setList(List<StringPair> list) {
        this.list = list;
    }

    public static class StringPair {
        String key;
        String value;

        public StringPair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}
