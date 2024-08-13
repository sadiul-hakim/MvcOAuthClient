package xyz.sadiulhakim.oauthmvcclient.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.sadiulhakim.oauthmvcclient.util.ApiClient;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final OAuth2AuthorizedClientService clientService;

    public UserController(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String getUsers(Model model, Authentication authentication) {

        var authenticationToken = (OAuth2AuthenticationToken) authentication;
        var client = clientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName());
        String token = client.getAccessToken().getTokenValue();
        List<User> userList = ApiClient.callApi("http://localhost:9093/user/", Map.of("Authorization", "Bearer " + token));

        model.addAttribute("users", userList);
        return "index";
    }
}
