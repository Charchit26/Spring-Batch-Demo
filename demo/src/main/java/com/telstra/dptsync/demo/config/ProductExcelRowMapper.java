package com.telstra.dptsync.demo.config;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class ProductExcelRowMapper implements RowMapper<Product> {

        public Product mapRow(RowSet rowSet) {
            Product product = new Product(
                    rowSet.getColumnValue(1),
                    rowSet.getColumnValue(2),
                    rowSet.getColumnValue(3),
                    rowSet.getColumnValue(4),
                    rowSet.getColumnValue(5),
                    rowSet.getColumnValue(6),
                    rowSet.getColumnValue(7),
                    rowSet.getColumnValue(12)
            );

            return product;
    }
}
