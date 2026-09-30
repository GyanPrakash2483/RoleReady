# RoleReady security audit

## Current controls
- Secrets are supplied through environment variables; .env is ignored.
- JWT authentication protects non-public endpoints.
- Passwords use BCrypt and are never persisted in plaintext.
- Uploads are capped at 10MB and PDF/DOCX magic bytes are validated.
- AI calls are backend-only and rate limited.
- API validation rejects blank/oversized job descriptions and invalid auth input.
- Generic API errors do not expose stack traces.
- Resume/JD/analysis session state is held temporarily and has TTL cleanup.

## Remaining operational controls
Production deployment still requires provider-side secret configuration, backups, monitoring, and a live deployment verification before release.
