# Project Notes

## JWT & Authentication

- Login flow: `AuthenticationServiceImpl` -> `JwtService.generateToken(Authentication)` (service/JwtService.java:32). The `JwtService` returns the token prefixed with `"Bearer "`.
- The `Authentication` principal is a `CustomUserDetail` (a concrete class that wraps the `User` entity via `new CustomUserDetail(user)`), never a `User` itself.
- To read the current user in a controller, use `@AuthenticationPrincipal CustomUserDetail customUserDetail` and call `customUserDetail.getUserId()`. Do NOT use `@AuthenticationPrincipal User user` — it resolves to `null` because the principal in the `SecurityContext` is a `CustomUserDetail`, not a `User`.
- The `userId` claim is added in `JwtService.generateToken` via `.claim("userId", userDetails.getUserId())`.
- `JwtTokenValidateFilter` is stateless: it builds the principal from the token claims via `CustomUserDetail.fromClaims(Claims)` and does NOT hit the DB per request. `CustomUserDetail` supports two modes: User-wrapped (login, via `CustomUserDetailService`) and claims-backed (filter, only `userId`/`username`/`authorities`; `getPassword()`/`getEmail()` return `null`).
- There is a duplicate JWT service (`JwtTokeServiceImpl`, implementing `JwtTokenService`) with the same logic. Consider deleting it to avoid confusion.
