# RoleReady operations

## Backups
Persistent MySQL data consists of account/auth metadata and guest usage. Production backups must be enabled at the managed MySQL provider with point-in-time recovery where available. Session resume/JD/analysis data is intentionally temporary and is not part of backup policy.

## Monitoring
The backend exposes `/actuator/health` and `/api/health`. Production monitoring should probe both and alert on non-2xx responses. GitHub Actions provides CI/CD failure visibility.

## Error monitoring
Application errors return sanitized messages to clients. Provider logs should be retained separately from user-visible responses; do not log resume contents, job descriptions, tokens, or API keys.

## Deployment verification
Before production release, run backend/frontend CI, build both images, verify the Render health endpoint, and exercise registration, login, resume upload, analysis, optimization, and export with a non-production account.
