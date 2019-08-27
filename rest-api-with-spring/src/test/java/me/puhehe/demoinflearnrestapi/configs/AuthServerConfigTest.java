package me.puhehe.demoinflearnrestapi.configs;


import me.puhehe.demoinflearnrestapi.accounts.Account;
import me.puhehe.demoinflearnrestapi.accounts.AccountRole;
import me.puhehe.demoinflearnrestapi.accounts.AccountService;
import me.puhehe.demoinflearnrestapi.common.BaseControllerTest;
import me.puhehe.demoinflearnrestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        // Given
        String username = "puhehe99@email.com";
        String password = "minhyung";
        Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN,AccountRole.USER))
                .build();

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId,clientSecret))
                    .param("username",username)
                    .param("password",password)
                    .param("grant_type","password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }

}