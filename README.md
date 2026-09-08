# RoleReady — Skeleton (V1)

Web app that scores a resume against a job description and generates an optimized resume.
Stack per `docs/specifications.md`: **Angular + Spring Boot + MySQL + Gemini 3.8 Flash** (via backend only).

## Layout

```text
RoleReady/
├── docs/specifications.md      # source of truth
├── .env.example                # ALL env vars (paste into Render dashboard, never commit .env)
├── backend/                    # Spring Boot 3.3 / Java 21 → Render Web Service (Docker)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/java/com/roleready/{auth,user,resume,job,analysis,optimization,ai,export,session,security,config,common}
└── frontend/                   # Angular 18 standalone → Render Static Site
    ├── package.json / angular.json
    └── src/app/{pages,services} + environments/
```

Backend modules map to spec §33; REST surface maps to §36; scoring weights to §14.

## Quickstart (local)

```bash
cp .env.example .env
# edit .env: SPRING_DATASOURCE_* (Aiven), JWT_SECRET, GEMINI_API_KEY, GOOGLE_*, MAIL_*

# Backend (needs Java 21 + Maven):
cd backend && mvn spring-boot:run

# Frontend (needs Node 22):
cd frontend && npm install && npm start
```

- Frontend: http://localhost:4200
- Backend: http://localhost:8080 (`/api/...`, Swagger at `/swagger-ui.html`)
- Health: backend `/actuator/health`

## Env vars

All in `.env.example`. Required for real use:

| Var | Used for |
|---|---|
| `SPRING_DATASOURCE_URL/USERNAME/PASSWORD` | Aiven MySQL connection + Flyway |
| `JWT_SECRET`, `JWT_EXPIRATION_MS` | auth tokens (NFR-SEC-001..003) |
| `GOOGLE_CLIENT_ID/SECRET/REDIRECT_URI` | Google OAuth (FR-AUTH-004) |
| `GEMINI_API_KEY`, `GEMINI_MODEL` | LLM via backend only (NFR-SEC-007) |
| `FRONTEND_URL`, `APP_CORS_ALLOWED_ORIGINS` | CORS + email links |
| `MAIL_HOST/PORT/USERNAME/PASSWORD/FROM` | verification + reset (FR-AUTH-002/005) |
| `APP_GUEST_MAX_USES=3` | guest limit (FR-GUEST-002) |
| `APP_SESSION_TTL_MINUTES` | temp-data expiry (FR-PRIV-005) |
| `APP_MAX_FILE_SIZE` | upload cap (NFR-PERF-004) |
| `API_BASE_URL` | frontend build-time backend URL |

## Hosting (Render + Aiven — everything external)

- **DB:** Aiven MySQL. Paste connection into `SPRING_DATASOURCE_URL/USERNAME/PASSWORD` on the backend service.
- **Backend:** Render Web Service from `backend/Dockerfile`. Set all `.env.example` vars in the Render dashboard (leave `PORT` unset — Render injects it). Needs only outbound HTTPS to Gemini + Aiven.
- **Frontend:** Render Static Site from `frontend/`. Build command `npm install && npm run build:prod`, publish directory `dist/roleready/browser`, with `API_BASE_URL=https://<your-backend>.onrender.com` (baked into `environment.prod.ts` at build time).

### Checklist before going live
- [ ] `JWT_SECRET` is long/random; `.env` never committed
- [ ] `GEMINI_API_KEY` set only on backend; never in frontend env
- [ ] CORS origins = your real frontend domain only
- [ ] Google OAuth redirect URI registered for prod backend URL
- [ ] SMTP set so verify/reset emails send
- [ ] MySQL has backups; Flyway `validate` passes
- [ ] Uploads capped (`APP_MAX_FILE_SIZE`), guest uses = 3

## Privacy note (spec §23/41/42)

Resumes, JDs, analyses, optimized outputs are **session-only** and must be deleted at session end.
Only `users / auth metadata / guest usage` persist (see `backend/.../db/migration/V1__init.sql`).
`SessionService` is an in-memory stub with TTL — swap for Redis/DB entities before production scale.

## Next implementation steps

1. Auth: verify-email, forgot/reset/change-password, Google code exchange, account delete.
2. Resume parsing per format + section detection; preserve MD/LaTeX structure.
3. AI prompts + JSON schema validation + scoring penalties for missing mandatory reqs.
4. Questions → answers → optimization diff (accept/reject) → PDF/MD/LaTeX export.
5. Guest-use counting (3/workflow, not per LLM call) + session-deletion wiring.
