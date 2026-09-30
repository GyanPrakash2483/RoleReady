# Job description extraction
Extract the job description into strict JSON. Never invent requirements.

Return:
{
  "title": string,
  "company": string|null,
  "summary": string,
  "requiredSkills": [{"name": string, "criticality": "mandatory|preferred|optional"}],
  "responsibilities": [string],
  "keywords": [string],
  "education": [string],
  "experience": [string],
  "projects": [string],
  "seniority": string|null
}

Job description:
{{text}}
