# AI data retention verification

RoleReady sends resume/JD/analysis content to the configured Gemini API only for the active request. The application does not write resume, JD, analysis, optimized output, or AI responses to persistent database tables. Temporary in-memory session state has TTL cleanup.

Persistent tables are limited to account/auth metadata, guest usage, and token metadata. Production logs must not contain prompt bodies, resume contents, JD contents, tokens, or provider API keys.
