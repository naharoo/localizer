package com.naharoo.localizer.mapper;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleModificationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceCreationRequest;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.domain.resource.ResourceSearchRequest;
import com.naharoo.localizer.endpoint.locale.LocaleCreationRequestDto;
import com.naharoo.localizer.endpoint.locale.LocaleDto;
import com.naharoo.localizer.endpoint.locale.LocaleModificationRequestDto;
import com.naharoo.localizer.endpoint.locale.LocaleSearchRequestDto;
import com.naharoo.localizer.endpoint.resource.ResourceCreationRequestDto;
import com.naharoo.localizer.endpoint.resource.ResourceDto;
import com.naharoo.localizer.endpoint.resource.ResourceModificationRequestDto;
import com.naharoo.localizer.endpoint.resource.ResourceSearchRequestDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

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
        factory.classMap(LocaleCreationRequest.class, LocaleCreationRequestDto.class)
            .constructorB("key", "name")
            .byDefault()
            .register();
        factory.classMap(LocaleSearchRequest.class, LocaleSearchRequestDto.class)
            .constructorB("query", "from", "size", "sortField", "sortOrder")
            .byDefault()
            .register();
        factory.classMap(LocaleModificationRequest.class, LocaleModificationRequestDto.class)
            .constructorB("id", "key", "name")
            .byDefault()
            .register();

        // Resources
        factory.classMap(Resource.class, ResourceDto.class)
            .constructorB("id", "key", "locale", "value", "created", "updated", "deleted")
            .byDefault()
            .register();
        factory.classMap(ResourceCreationRequest.class, ResourceCreationRequestDto.class)
            .constructorB("key", "localeId", "value")
            .byDefault()
            .register();
        factory.classMap(ResourceModificationRequest.class, ResourceModificationRequestDto.class)
            .constructorB("id", "key", "value")
            .byDefault()
            .register();
        factory.classMap(ResourceSearchRequest.class, ResourceSearchRequestDto.class)
            .constructorB("query", "localeQuery", "from", "size", "sortField", "sortOrder")
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