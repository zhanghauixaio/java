package com.seven.jasperreport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MarkPrintRespVo {
    private String powerGear;
    private String workOrderNo;
    private String moduleType;
    private String currentGear;
    private String trayNo;
    private List<String> barcodes;
    private String packingSize;
    private String netWeight;
    private String grossWeight;
    private String trayQty;
    private String finishProductCode;

    public static Collection<MarkPrintRespVo> createBeanCollection() {
        List<MarkPrintRespVo> respVos = new ArrayList<>();
        MarkPrintRespVo markPrintRespVo = new MarkPrintRespVo();
        List<String> barcodes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            barcodes.add(String.valueOf(i));
        }
        markPrintRespVo.setBarcodes(barcodes);
        markPrintRespVo.setWorkOrderNo("who");
        markPrintRespVo.setModuleType("ja");
        markPrintRespVo.setCurrentGear("hso");
        markPrintRespVo.setTrayNo("hoahg");

        respVos.add(markPrintRespVo);
        return respVos;
    }

    public String getPowerGear() {
        return powerGear;
    }

    public void setPowerGear(String powerGear) {
        this.powerGear = powerGear;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getCurrentGear() {
        return currentGear;
    }

    public void setCurrentGear(String currentGear) {
        this.currentGear = currentGear;
    }

    public String getTrayNo() {
        return trayNo;
    }

    public void setTrayNo(String trayNo) {
        this.trayNo = trayNo;
    }

    public List<String> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<String> barcodes) {
        this.barcodes = barcodes;
    }

    public String getPackingSize() {
        return packingSize;
    }

    public void setPackingSize(String packingSize) {
        this.packingSize = packingSize;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getTrayQty() {
        return trayQty;
    }

    public void setTrayQty(String trayQty) {
        this.trayQty = trayQty;
    }

    public String getFinishProductCode() {
        return finishProductCode;
    }

    public void setFinishProductCode(String finishProductCode) {
        this.finishProductCode = finishProductCode;
    }
}
