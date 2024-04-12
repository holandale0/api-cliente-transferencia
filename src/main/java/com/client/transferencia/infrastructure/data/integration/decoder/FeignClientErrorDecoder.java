package com.client.transferencia.infrastructure.data.integration.decoder;

import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
public class FeignClientErrorDecoder implements ErrorDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientErrorDecoder.class);

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String responseBody = IOUtils.toString(response.body().asReader());
        String url = response.request().url();

        LOGGER.info("Response URL: {}", url);
        LOGGER.info("Response Status: {}", status);
        LOGGER.info("Response Body: {}", responseBody);

        try {
            ObjectMapper mapper = new ObjectMapper();
            FeignClientExceptionModel feignClienteException = mapper.readValue(responseBody, FeignClientExceptionModel.class);
            return new InfrastructureException(String.join(", ", feignClienteException.getMessage()));
        } catch (Exception e){
            LOGGER.error("Erro ao converter JSON de retorno", e);
            return new InfrastructureException(MessageFormat.format("Erro ao comunicar com serviço externo ({0}), tente novamente mais tarde. ",
                    response.status()));
        }
    }
}