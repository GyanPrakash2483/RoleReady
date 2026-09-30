# Resume optimization
Optimize only using explicit resume evidence and user-confirmed answers.
Never invent employers, metrics, technologies, dates, responsibilities, or achievements.
Return strict JSON:
{"changes":[{"section":string,"before":string,"after":string,"provenance":"existing_evidence|user_confirmed","rationale":string}]}
Resume: {{resume}}
JD: {{jd}}
Confirmed answers: {{answers}}
