# Introduction

OAuth2 Server bases on Java Spring Security and MySql.

This is the user guide for the support for OAuth 2.0.


# OAuth 2.0 Provider

The OAuth 2.0 provider is responsible for managing the OAuth 2.0 clients that can access its protected resources on behalf of a user. The provider does this by managing and verifying the OAuth 2.0 tokens that can be used to access the protected resources. Where applicable, the provider must also supply an interface for the user to confirm that a client can be granted access to the protected resources (i.e. a confirmation page).


## Managing Clients

The entry point into your database of clients is defined by the ClientDetailsService. You must define your own ClientDetailsService that will load ClientDetails by the client id. Note the existence of an in-memory implementation of ClientDetailsService.
When implementing your ClientDetailsService consider returning instances of (or extending) BaseClientDetails.


## Managing Tokens

The OAuth2ProviderTokenServices interface defines the operations that are necessary to manage OAuth 2.0 tokens. Note the following:
When an access token is created, the authentication must be stored so that the subsequent access token can reference it.
The access token is used to load the authentication that was used to authorize its creation.

When creating your OAuth2ProviderTokenServices implementation, you may want to consider extending the RandomValueOAuth2ProviderTokenServices which creates tokens via random value and handles everything except for the persistence of the tokens. There is also an in-memory implementation of the OAuth2ProviderTokenServices that may be suitable.


## OAuth 2.0 Provider Request Filters

The requests for the tokens and for access to protected resources are handled by standard Spring Security request filters. The following filters are required in the Spring Security filter chain in order to implement OAuth 2.0:
  * The OAuth2AuthorizationFilter is used to service the request for an access token. Default URL: /oauth/authorizen.
  * The OAuth2ExceptionHandlerFilter is used to handle any errors.
  * The OAuth2ProtectedResourceFilter is used to load the Authentication for the request given an authenticated access token.
Other filters are applied according to the different OAuth 2.0 flows.


## Provider Configuration

For the OAuth 2.0 provider, configuration is simplified using the custom spring configuration elements. The schema for these elements rests at http://spring-security-oauth.codehaus.org/schema/spring-security-oauth2-1.0.xsd. The namespace is http://spring-security-oauth.codehaus.org/oauth2/1.0.

The provider element is used to configure the OAuth 2.0 provider mechanism. The following attributes can be applied to the provider element:
  * client-details-service-ref: The reference to the bean that defines the client details service. This is required if not autowired.
  * token-services-ref: The reference to the bean that defines the token services.
  * authorization-url: The URL at which a request for an authorization token will be serviced.
  
The provider element is also used to configure each OAuth 2.0 flow. It takes a flows child element that has as its child elements the configuration for each flow. This configuration is documented in the documentation section of each flow.

The client-details-service element is used to define an in-memory implementation of the client details service. It takes an id attribute and an arbitrary number of client child elements that define the following attributes for each client:
  * clientId (required): The client id.
  * secret (required): The client secret, if any.
  * scope: The scope to which the client is limited (comma-separated). If scope is undefined or empty (the default) the client is not limited by scope.
  * authorizedFlows: Flows that are authorized for the client to use (comma-separated). Default value is "web_server".
  * authorities: Authorities that are granted to the client (comma-separated).


## Web Server Flow

The Web Server OAuth 2.0 flow is designed for the situation where both the OAuth client and the OAuth provider are two online web applications. For details, see section 3.6 of the spec. It is configured using the web_server configuration element of the flows configuration element of the provider.

To support the Web Server flow, there must be an endpoint to which the user can be redirected by the client for authentication of an access token. The WebServerOAuth2Filter is used to process this endpoint, but the developer must supply the UI that the user will see in order to confirm or deny the request. The location of this UI is supplied with the user-approval-page attribute of the web_server configuration element. The page must simply submit an approval request parameter with a value of "true" to the access token authentication endpoint. The parameter name is configured with the approval-parameter-name configuration attribute (default value: "user_oauth_approval").

Also to support the Web Server flow, there must be some kind of service used to supply verification codes. The VerificationCodeServices interface is used to define this service. By default, an in-memory implementation is provided, but you can create your own custom service and integrate it with the verification-code-services-ref configuration attribute.