package pe.gob.onpe.rae.helper;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public class TypesUtil {
    public static Long getDefaultLong(Object objValue){
        if(objValue instanceof Long){
            return (Long) objValue;
        }
        try {
            if(objValue != null && StringUtils.isNotBlank(objValue.toString()) && !objValue.toString().equals("null")){
                return Long.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
