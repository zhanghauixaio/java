package com.seven.jasperreport;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Collection;
import java.util.List;

public class DailySalesDataSource implements JRDataSource {

    /**
     * 测试数据，实际项目中是动态获取，也不一定是数组，可以是其它的数据类型 .
     */
    private static MarkPrintRespVo vo;
    static {
        vo = new MarkPrintRespVo();
        vo.setPowerGear("580M");
        vo.setWorkOrderNo("O12041");
    }
    private List<MarkPrintRespVo> list = (List<MarkPrintRespVo>) MarkPrintRespVo.createBeanCollection();
    private int index = -1;

    public DailySalesDataSource() {
    }

    /**
     * 实现了 JRDataSource 中的方法．判断是否还有下一个．
     */
    public boolean next() throws JRException {
        return false;
    }

    /**
     * 实现了 JRDataSource 中的方法．
     *
     * @param field 是对应报表中的要填充的字段的名称．
     */
    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;

        String fieldName = field.getName();
        if ("powerGear".equals(fieldName)) {
            value = vo.getPowerGear();
        }

        return value;
    }
}