package com.seven.office.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class CustomizeColumnWidth extends AbstractColumnWidthStyleStrategy {
    //自定义列宽
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean isHead) {
        // 测试为 COLUMN 宽度定制.
        if (isHead && cell.getRowIndex() == 2) {
            int columnWidth = cell.getStringCellValue().getBytes().length;
            int cellIndex = cell.getColumnIndex();
            switch (cellIndex) {
                case 0:
                case 2:
                case 3:
                    columnWidth = 10;
                    break;
                case 1:
                    columnWidth = 12;
                    break;
                case 4:
                    columnWidth = 15;
                    break;
                case 5:
                    columnWidth = 50;
                    break;
                default:
                    break;
            }

            if (columnWidth > 255) {
                columnWidth = 255;
            }
            writeSheetHolder.getSheet().setColumnWidth(cellIndex, columnWidth * 256);
        }
    }

    //自定义行高
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        // 设置行高测试
        int rowIndex = row.getRowNum();
        System.out.println("当前行: " + rowIndex);
        short height = 600;
        row.setHeight(height);
    }
}

