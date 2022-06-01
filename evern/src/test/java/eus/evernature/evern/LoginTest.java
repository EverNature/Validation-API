package eus.evernature.evern;

import static org.junit.Assert.assertNotNull;

import java.net.http.HttpHeaders;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import eus.evernature.evern.models.json_responses.TokenDuple;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldCreateMockMvc() {
    assertNotNull(mockMvc);
  }

  @Test
  void shouldReturnToken() throws Exception {
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    String url = "/api/login";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    String username = "testUser";
    String password = "12345678aA@";

    params.add("username", username);
    params.add("password", password);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
        .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
        .params(params))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();

    TokenDuple tokenDuple = new Gson().fromJson(content, TokenDuple.class);

    String accesToken = "Bearer ".concat(tokenDuple.getAccess_token());

    url = "/api/prediction/prediction-types";

    result = mockMvc.perform(MockMvcRequestBuilders.get(url)
    .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
    .header("Authorization", accesToken))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andReturn();

    url = "/api/prediction/detected-species";

    result = mockMvc.perform(MockMvcRequestBuilders.get(url)
    .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
    .header("Authorization", accesToken))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andReturn();

    url = "/api/record/today";

    result = mockMvc.perform(MockMvcRequestBuilders.get(url)
    .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
    .header("Authorization", accesToken))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andReturn();

    url = "/api/prediction/animal-predicted";

    result = mockMvc.perform(MockMvcRequestBuilders.get(url)
    .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
    .header("Authorization", accesToken))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andReturn();
  }

  @Test
  void shouldReturnFailedLogin() throws Exception {
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    String url = "/api/login";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    String username = "testUser";
    String password = "testIncorrect";

    params.add("username", username);
    params.add("password", password);

    mockMvc.perform(MockMvcRequestBuilders.post(url)
        .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
        .params(params))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  void incorrectTokenIsInvalid() throws Exception {
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    String url = "/api/login";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    String username = "testUser";
    String password = "12345678aA@";

    params.add("username", username);
    params.add("password", password);

    mockMvc.perform(MockMvcRequestBuilders.post(url)
        .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
        .params(params))
        .andExpect(MockMvcResultMatchers.status().isOk());

    String accesToken = "Bearer ".concat("tokentoskenasksfku3278r3ife");

    url = "/api/prediction/prediction-types";

    mockMvc.perform(MockMvcRequestBuilders.get(url)
    .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
    .header("Authorization", accesToken))
    .andExpect(MockMvcResultMatchers.status().isForbidden());
  }
}