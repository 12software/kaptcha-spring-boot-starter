package com.software12.kaptcha.autoconfigure;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.code.kaptcha.util.Config;
import com.software12.kaptcha.constant.KaptchaConst;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConditionalOnProperty(prefix = KaptchaConst.AUTO_CONFIG_PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfigure {

    @Bean
    public Producer defaultKaptcha(KaptchaProperties kaptchaProperties) {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Config config = new Config(kaptchaPropertiesToProperties(kaptchaProperties));
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    @ConditionalOnProperty(prefix = KaptchaConst.AUTO_CONFIG_PREFIX, value = "session.enabled", havingValue = "true")
    public ServletRegistrationBean<KaptchaServlet> kaptchaServlet(KaptchaProperties kaptchaProperties) {
        return new ServletRegistrationBean<>(new KaptchaServlet(), kaptchaProperties.getServlet().getPath());
    }

    private Properties kaptchaPropertiesToProperties(KaptchaProperties kaptchaProperties) {
        Properties properties = new Properties();

        properties.setProperty(Constants.KAPTCHA_SESSION_CONFIG_KEY, kaptchaProperties.getSession().getKey());
        properties.setProperty(Constants.KAPTCHA_SESSION_CONFIG_DATE, kaptchaProperties.getSession().getDate());

        properties.setProperty(Constants.KAPTCHA_BORDER, String.valueOf(kaptchaProperties.getBorder().getEnabled()));
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, kaptchaProperties.getBorder().getColor());
        properties.setProperty(Constants.KAPTCHA_BORDER_THICKNESS, String.valueOf(kaptchaProperties.getBorder().getThickness()));

        properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, kaptchaProperties.getNoise().getColor());
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, kaptchaProperties.getNoise().getImpl());

        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, kaptchaProperties.getObscurificator().getImpl());

        properties.setProperty(Constants.KAPTCHA_PRODUCER_IMPL, kaptchaProperties.getProducer().getImpl());

        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, kaptchaProperties.getTextProducer().getImpl());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, kaptchaProperties.getTextProducer().getCharacter().getString());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, String.valueOf(kaptchaProperties.getTextProducer().getCharacter().getLength()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, String.valueOf(kaptchaProperties.getTextProducer().getCharacter().getSpace()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, String.join(",", kaptchaProperties.getTextProducer().getFont().getNames()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, kaptchaProperties.getTextProducer().getFont().getColor());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, String.valueOf(kaptchaProperties.getTextProducer().getFont().getSize()));

        properties.setProperty(Constants.KAPTCHA_WORDRENDERER_IMPL, kaptchaProperties.getWord().getImpl());

        properties.setProperty(Constants.KAPTCHA_BACKGROUND_IMPL, kaptchaProperties.getBackground().getImpl());
        properties.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, kaptchaProperties.getBackground().getColorFrom());
        properties.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, kaptchaProperties.getBackground().getColorTo());

        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, String.valueOf(kaptchaProperties.getImage().getWidth()));
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, String.valueOf(kaptchaProperties.getImage().getHeight()));

        return properties;
    }

}