package cn.jiweiqing.base.config.param;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ParamsValueTrimRequestWrapper extends HttpServletRequestWrapper {

    private Map<String , String[]> mParams = new HashMap<>();
    private ServletInputStream mInputStream;
    private BufferedReader mReader;

    public ParamsValueTrimRequestWrapper(HttpServletRequest request) {
        super(request);
        readFromBody(request);
        initParams(request);
    }

    private void initParams(HttpServletRequest request){
        Map<String, String[]> originParams = request.getParameterMap();
        for(Map.Entry<String,String[]> entry : originParams.entrySet()){
            mParams.put(entry.getKey(),trimValues(entry.getValue()));
        }
    }

    private String[] trimValues(String[] values){
        if(values!=null){
            for(int i=0;i<values.length;i++){
                values[i] = values[i]==null?null:values[i].trim();
            }
        }
        return values;
    }

    private void readFromBody(HttpServletRequest request){
        try{
            boolean isPostMethod = request.getMethod().equalsIgnoreCase("POST");
            String contentType = StringUtils.isEmpty(request.getContentType())?null:request.getContentType().split(";")[0].trim().toLowerCase();
            boolean isTextContent =
                    MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType) ||
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType);
            if(isPostMethod && isTextContent){
                String body = IOUtil.toString(request.getInputStream());
                request.setAttribute("body",body);
                mInputStream = new MServletInputStream(body==null?null:body.getBytes("UTF-8"));
                mReader = new BufferedReader(new InputStreamReader(mInputStream));
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getParameter(String name) {
        String[] values = mParams.get(name);
        return values==null?null:values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return mParams.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return mParams;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return mInputStream !=null? mInputStream :super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return mReader !=null? mReader :super.getReader();
    }

    private static final class MServletInputStream extends ServletInputStream {

        private byte[] data;
        private int offset = -1;

        public MServletInputStream(byte[] data){
            this.data = data;
        }

        @Override
        public boolean isFinished() {
            return data==null || (offset==data.length-1);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return isFinished()?-1:data[++offset];
        }

    }

}
