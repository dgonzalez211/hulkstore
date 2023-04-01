package com.diegodev.hulkstore.components.appnav.util;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleToLongConverter implements Converter<Long, Double> {

    @Override
    public Result<Double> convertToModel(Long value, ValueContext context) {
        return Result.ok(value.doubleValue());
    }

    @Override
    public Long convertToPresentation(Double value, ValueContext context) {
        return value.longValue();
    }

}

