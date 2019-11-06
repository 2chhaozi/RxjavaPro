package cn.liuzehao.rxjavapro.RetrofitBaseUse.JinShanPra;

/**
 * Created by liuzehao on 2019/8/5.
 */
public class Translation {
    private int status;
    private Content content;
    private static class Content{
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;

        @Override
        public String toString() {
            return "Content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", errNo=" + errNo +
                    '}';
        }

    }

    public String show(){
        return "Translation//show//"+this;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }

}
