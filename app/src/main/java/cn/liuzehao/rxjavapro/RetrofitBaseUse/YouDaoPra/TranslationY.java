package cn.liuzehao.rxjavapro.RetrofitBaseUse.YouDaoPra;

import java.util.List;

/**
 * Created by liuzehao on 2019/8/5.
 */
public class TranslationY {
    private String type;
    private int errorCode;
    private int elapsedTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<List<TranslateResultBean>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(List<List<TranslateResultBean>> translateResult) {
        this.translateResult = translateResult;
    }

    private List<List<TranslateResultBean>> translateResult;

    private static class TranslateResultBean{
       private String src;
       private String tgt;

       public String getSrc() {
           return src;
       }

       public void setSrc(String src) {
           this.src = src;
       }

       public String getTgt() {
           return tgt;
       }

       public void setTgt(String tgt) {
           this.tgt = tgt;
       }


       @Override
       public String toString() {
           return "TranslateResultBean{" +
                   "src='" + src + '\'' +
                   ", tgt='" + tgt + '\'' +
                   '}';
       }
   }

    @Override
    public String toString() {
        return "TranslationY{" +
                "type='" + type + '\'' +
                ", errorCode=" + errorCode +
                ", elapsedTime=" + elapsedTime +
                ", translateResult=" + translateResult +
                '}';
    }
}
