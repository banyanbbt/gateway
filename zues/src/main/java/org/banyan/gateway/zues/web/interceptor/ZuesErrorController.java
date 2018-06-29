package org.banyan.gateway.zues.web.interceptor;

import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.util.UniqueIdUtil;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.gateway.zues.message.Message;
import org.banyan.gateway.zues.message.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.context.embedded.AbstractEmbeddedServletContainerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Basic global error {@link Controller}, rendering {@link ErrorAttributes}. More specific
 * errors can be handled either using Spring MVC abstractions (e.g.
 * {@code @ExceptionHandler}) or by adding servlet
 * {@link AbstractEmbeddedServletContainerFactory#setErrorPages container error pages}.
 *
 * copy from org.springframework.boot.autoconfigure.web.BasicErrorController, modified by levi
 * @author Dave Syer
 * @author Phillip Webb
 * @author Michael Stummvoll
 * @author Stephane Nicoll
 * @author levi
 * @see ErrorAttributes
 * @see ErrorProperties
 */
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ZuesErrorController extends AbstractErrorController {
    @Autowired
    private RSAUtil rsaUtil;

    private final ErrorProperties errorProperties;

    /**
     * Create a new {@link org.springframework.boot.autoconfigure.web.BasicErrorController} instance.
     * @param errorAttributes the error attributes
     * @param errorProperties configuration properties
     */
    public ZuesErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        this(errorAttributes, errorProperties, Collections.emptyList());
    }

    /**
     * Create a new {@link org.springframework.boot.autoconfigure.web.BasicErrorController} instance.
     * @param errorAttributes the error attributes
     * @param errorProperties configuration properties
     * @param errorViewResolvers error view resolvers
     */
    public ZuesErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping(produces = "text/html")
    public Message errorHtml(HttpServletRequest request, HttpServletResponse response) {
       return this.buildMessage(request);
    }

    @RequestMapping
    @ResponseBody
    public Message error(HttpServletRequest request) {
        return this.buildMessage(request);
    }

    /**
     * 构造异常Message
     * @param request
     * @return
     */
    private Message buildMessage(HttpServletRequest request) {
        Record record = new Record(SubmitCode.SYSTEM_ERROR, rsaUtil);
        record.setGid(UniqueIdUtil.getUniqueId());

        HttpStatus status = getStatus(request);
        if (HttpStatus.METHOD_NOT_ALLOWED == status || HttpStatus.NOT_FOUND == status) {
            record.setSubmiCode(SubmitCode.PATH_FAILED);
            return record.buildMessage();
        }
        return record.buildMessage();
    }

    /**
     * Determine if the stacktrace attribute should be included.
     * @param request the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
//    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
//        IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
//        if (include == IncludeStacktrace.ALWAYS) {
//            return true;
//        }
//        if (include == IncludeStacktrace.ON_TRACE_PARAM) {
//            return getTraceParameter(request);
//        }
//        return false;
//    }

    /**
     * Provide access to the error properties.
     * @return the error properties
     */
//    protected ErrorProperties getErrorProperties() {
//        return this.errorProperties;
//    }

}
