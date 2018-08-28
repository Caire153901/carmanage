package com.wmt.carmanage;

import com.wmt.carmanage.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * 测试基础模块
 */
@SuppressWarnings("all")
@WebAppConfiguration
@Slf4j
public class BaseTest {

    private String snippetDir = "target/generated-snippets";
    private String outputDir = "target/asciidoc";

    //注入webApplicationContext
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    //设置mockMvc
    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void Test() throws Exception {

    }

    public void doGet(String url, Map<String, String> params) {
        url += "?" + UrlUtils.convertParams(params);
        doExecute(get(url));
    }

    public void doPost(String url, Map<String, String> params) {
        String content = UrlUtils.convertParams(params);
        doExecute(post(url).content(content));
    }

    public void doPost(String url, Map<String, String> params, Map<String, String> headers) {
        String content = UrlUtils.convertParams(params);
        MockHttpServletRequestBuilder builder = post(url).content(content);
        if(headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        doExecute(builder);
    }

    public void doDelete(String url, Map<String, String> params) {
        String content = UrlUtils.convertParams(params);
        doExecute(delete(url).content(content));
    }

    public void doPut(String url, Map<String, String> params) {
        String content = UrlUtils.convertParams(params);
        doExecute(put(url).content(content));
    }

    private void doExecute(MockHttpServletRequestBuilder builder) {

//        RestDocumentationResultHandler get = MockMvcRestDocumentation.document("get", preprocessResponse(prettyPrint()));
        ResultHandler print = MockMvcResultHandlers.print();
//        System.out.println("--------" + get);
        try {
            ResultActions perform = mockMvc.perform(builder
                                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            .accept(MediaType.APPLICATION_JSON));
//                    .andExpect(status().isOk())
            perform.andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}