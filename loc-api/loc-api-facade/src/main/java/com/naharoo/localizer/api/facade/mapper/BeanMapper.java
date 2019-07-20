package com.naharoo.localizer.api.facade.mapper;

import com.naharoo.localizer.api.facade.endpoint.locale.LocaleDto;
import com.naharoo.localizer.api.facade.endpoint.locale.LocaleModificationRequestDto;
import com.naharoo.localizer.core.services.locale.Locale;
import com.naharoo.localizer.core.services.locale.LocaleModificationRequest;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.time.*;
import java.time.temporal.TemporalAmount;

@Component
public class BeanMapper extends ConfigurableMapper {

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void configure(final MapperFactory factory) {
        registerPassThroughConverters(factory);

        // Locales
        factory.classMap(Locale.class, LocaleDto.class)
            .constructorB("id", "key", "name", "created", "updated", "deleted")
            .byDefault()
            .register();

        factory.classMap(LocaleModificationRequest.class, LocaleModificationRequestDto.class)
            .constructorB("key", "name")
            .byDefault()
            .register();
    }

    private void registerPassThroughConverters(final MapperFactory factory) {
        final ConverterFactory converterFactory = factory.getConverterFactory();
        converterFactory.registerConverter(new PassThroughConverter(YearMonth.class));
        converterFactory.registerConverter(new PassThroughConverter(LocalDate.class));
        converterFactory.registerConverter(new PassThroughConverter(LocalDateTime.class));
        converterFactory.registerConverter(new PassThroughConverter(LocalTime.class));
        converterFactory.registerConverter(new PassThroughConverter(TemporalAmount.class));
        converterFactory.registerConverter(new PassThroughConverter(Duration.class));
        converterFactory.registerConverter(new PassThroughConverter(Period.class));
    }
}
