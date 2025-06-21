package com.ecommerce.configuration;

public class SwaggerDescription {

    public static final String AUTHENTICATION_GUIDE = """
        ## 🔐 Authentication Instructions (JWT)

        To use protected endpoints (e.g. `/cart`, `/orders`, `/admin`), follow these steps:

        1. **Register a new user** via `POST /auth/register`
           – This will create an account **and return a JWT token**.
           Use this if you're a new user.

        2. **OR** log in an existing user via `POST /auth/login`
           – This will also return a **JWT token** for a previously registered account.

        3. Click the **Authorize** button (top-right corner in Swagger UI)

        4. Paste **only the token value** into the field labeled `Value:` (without `Bearer` prefix)

        5. Click **Authorize** → Now you can access all endpoints allowed for your role.

        ---

        🔒 **Access Control:**
        - `/products`, `/auth/**` – Publicly accessible
        
        - `/cart/**`, `/orders/**` – Requires authentication (USER or ADMIN)
        
        - `/admin/**` – Requires **ADMIN** role
        """;
}