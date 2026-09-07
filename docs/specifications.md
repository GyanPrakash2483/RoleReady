# Software Requirements Specification — RoleReady

**Version:** 1.0
**Product:** RoleReady
**Document Type:** Software Requirements Specification
**Status:** Initial Product Specification
**Primary Platform:** Web Application
**Target Users:** Job seekers

---

# 1. Introduction

## 1.1 Purpose

RoleReady is a web-based application that helps job seekers evaluate and specialize their resumes for specific job descriptions using Large Language Models (LLMs).

The system accepts a user's existing resume or allows the user to create one from scratch. The user then provides a job description as text. RoleReady analyzes both, determines how well the candidate's resume aligns with the requirements of the role, identifies strengths and weaknesses, asks targeted questions when additional information could improve the resume, and generates an optimized version of the resume.

The system is designed around the principle that **a resume should communicate the candidate's most relevant qualifications for a specific role without unnecessarily discarding useful information or changing the candidate's intended resume format**.

---

## 1.2 Product Vision

RoleReady should transform:

> "Here is my resume and here is a job description."

into:

> "Here is how well your resume fits this role, what is missing, what could be improved, what information we need from you, and here is a better version of your resume."

The product should therefore function as both:

1. A **resume evaluation system**, and
2. A **role-specific resume optimization system**.

---

# 2. Scope

## 2.1 In Scope

RoleReady V1 shall provide:

* User registration and authentication
* Email/password authentication
* Google OAuth authentication
* Guest usage
* Three free uses without authentication
* Resume upload
* Resume creation from scratch
* Resume parsing
* Editable structured resume representation
* Support for:

  * PDF
  * DOCX
  * TXT
  * Markdown
  * LaTeX
* Preservation of source formatting where possible
* Strict preservation of Markdown/LaTeX formatting when supplied
* Job description input through pasted text
* Automatic extraction of job information
* Resume/JD semantic matching
* Resume evaluation
* Numerical scoring
* Skill analysis
* Requirement analysis
* Keyword analysis
* ATS analysis
* Experience relevance analysis
* Project relevance analysis
* Achievement/impact analysis
* Education alignment
* Seniority alignment
* Clarity/readability analysis
* AI-generated improvement recommendations
* AI-generated clarification questions
* User confirmation before potentially fabricated information is incorporated
* Aggressive resume optimization
* Before/after comparison
* Individual modification acceptance/rejection
* Final resume generation
* PDF export
* Markdown export when appropriate
* LaTeX export when appropriate
* Automatic deletion of session data
* Account management
* Password reset
* Email verification
* Password change
* Account deletion

---

## 2.2 Out of Scope for V1

The following are explicitly excluded:

* Persistent resume storage
* Persistent job-description storage
* Application tracking
* Job-board integration
* Automatic job searching
* Recruiter accounts
* Recruiter dashboards
* Persistent analysis history
* Persistent optimized resumes
* User resume libraries

---

# 3. User Types

## 3.1 Guest User

A guest user can use RoleReady without creating an account.

A guest may perform **three complete RoleReady workflows**.

After the third completed use, the fourth attempt requires authentication.

Guest data exists only for the current browser session and is deleted when the browser/tab session ends.

---

## 3.2 Authenticated User

An authenticated user can use the application beyond the three-use guest limit.

Authentication methods:

* Email/password
* Google OAuth

Authentication does **not** imply permanent storage of resumes.

A logged-in user's resume and job description are still session data and must be deleted when the session ends.

Logging out is not required for session data deletion.

---

# 4. Core User Journey

The primary workflow shall be:

```text
                  RoleReady
                     │
                     ▼
             Create / Upload Resume
                     │
                     ▼
              Resume Processing
                     │
                     ▼
             Paste Job Description
                     │
                     ▼
               JD Processing
                     │
                     ▼
             Resume/JD Analysis
                     │
                     ▼
             Role Readiness Score
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
       Strengths   Weaknesses   Missing
                              Evidence
          │          │          │
          └──────────┼──────────┘
                     ▼
             AI Recommendations
                     │
                     ▼
          AI-generated Questions
                     │
                     ▼
              User Answers
                     │
                     ▼
             Resume Optimization
                     │
                     ▼
            Review Changes
                     │
                     ▼
             Accept / Reject
                     │
                     ▼
             Final Resume
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
         PDF         MD        LaTeX
```

---

# 5. Functional Requirements

## 5.1 Authentication

### FR-AUTH-001 — User Registration

The system shall allow users to create an account using an email address and password.

### FR-AUTH-002 — Email Verification

The system shall support email verification.

### FR-AUTH-003 — Login

The system shall allow registered users to authenticate using email/password.

### FR-AUTH-004 — Google Authentication

The system shall support authentication through Google OAuth 2.0.

### FR-AUTH-005 — Password Reset

The system shall allow users to reset forgotten passwords.

### FR-AUTH-006 — Change Password

Authenticated users shall be able to change their password.

### FR-AUTH-007 — Account Deletion

Authenticated users shall be able to delete their RoleReady account.

### FR-AUTH-008 — Session Independence

Logging out shall not be the mechanism used to trigger resume-data deletion.

Resume data shall be treated as temporary session data.

---

# 6. Guest Usage

### FR-GUEST-001

Unauthenticated users shall be permitted to use RoleReady without creating an account.

### FR-GUEST-002

A guest shall receive three free complete workflows.

### FR-GUEST-003

A "use" shall represent one complete resume/JD analysis and optimization workflow rather than one individual LLM request.

### FR-GUEST-004

The fourth workflow attempt shall require authentication.

### FR-GUEST-005

The system shall display an authentication prompt when the guest reaches the usage limit.

Example:

> You've used your 3 free RoleReady analyses. Sign in to continue.

### FR-GUEST-006

Internal LLM requests shall not independently consume user credits.

---

# 7. Resume Input

## 7.1 File Upload

### FR-RES-001

The system shall allow users to upload resumes in:

* PDF
* DOCX
* TXT
* MD
* LaTeX

### FR-RES-002

The system shall validate uploaded file types.

### FR-RES-003

The system shall extract resume content from supported files.

### FR-RES-004

The system shall identify resume sections automatically.

Potential sections include:

* Personal information
* Summary
* Objective
* Experience
* Education
* Skills
* Projects
* Achievements
* Certifications
* Publications
* Open source
* Coursework
* Volunteer experience
* Custom sections

The system shall not restrict users to these sections.

---

# 8. Resume Creation

### FR-RES-010

Users shall be able to create a resume without uploading an existing document.

### FR-RES-011

The resume editor shall provide structured fields for common resume components.

### FR-RES-012

Users shall be able to create custom sections.

### FR-RES-013

Users shall be able to edit resume content.

### FR-RES-014

Users shall not be required to use only predefined sections.

### FR-RES-015

The system shall support multiple entries within sections such as experience, education, and projects.

---

# 9. Resume Format Preservation

### FR-RES-020

For uploaded resumes, RoleReady shall preserve the original formatting as far as technically possible.

### FR-RES-021

For Markdown input, the system shall preserve the Markdown structure and formatting when generating an optimized Markdown resume.

### FR-RES-022

For LaTeX input, the system shall preserve the original LaTeX structure and formatting as strictly as possible.

### FR-RES-023

The system shall modify relevant content without unnecessarily restructuring the user's LaTeX source.

### FR-RES-024

The system shall preserve user-provided templates whenever technically feasible.

### FR-RES-025

The system shall generate a new formatted representation when exact preservation of a binary format is technically impossible.

---

# 10. Job Description

### FR-JD-001

The user shall provide the job description by pasting text.

### FR-JD-002

The system shall parse the supplied job description.

### FR-JD-003

The system shall extract all relevant information available in the job description.

The extracted information may include:

* Company
* Job title
* Location
* Employment type
* Experience requirements
* Education requirements
* Required skills
* Preferred skills
* Responsibilities
* Qualifications
* Benefits
* Salary information
* Keywords
* Technical requirements
* Soft skills
* Seniority
* Domain requirements

### FR-JD-004

The system shall distinguish between explicitly stated and inferred requirements.

---

# 11. Resume/JD Analysis

RoleReady shall perform a structured comparison between the resume and job description.

### FR-ANL-001

The system shall identify requirements explicitly mentioned by the employer.

### FR-ANL-002

The system shall identify corresponding evidence in the resume.

### FR-ANL-003

The system shall classify requirements into categories such as:

* Strong match
* Partial match
* Weak match
* Missing
* Unclear

### FR-ANL-004

The system shall provide evidence supporting important matching decisions.

Example:

```text
Requirement:
REST API development

Resume evidence:
Backend project using Node.js and Express

Assessment:
Strong match
```

### FR-ANL-005

The system shall identify requirements for which the resume provides insufficient evidence.

---

# 12. Evaluation Categories

RoleReady shall evaluate at minimum:

1. Required skills
2. Preferred skills
3. Experience relevance
4. Responsibility alignment
5. Keyword coverage
6. Education alignment
7. Project relevance
8. Achievement/impact
9. Seniority alignment
10. ATS compatibility
11. Clarity
12. Readability
13. Resume quality
14. Overall role alignment

---

# 13. Role Readiness Score

RoleReady shall provide a numerical score representing the resume's alignment with the supplied role.

## 13.1 Score

The score shall be expressed as:

> **Role Readiness: X/100**

The score shall be calculated by the application rather than being blindly accepted from the LLM.

The LLM provides structured evidence and assessments; Spring Boot applies the scoring methodology.

---

# 14. Proposed Scoring Model

The initial scoring model shall be:

| Category                 |   Weight |
| ------------------------ | -------: |
| Required Skills          |      20% |
| Relevant Experience      |      15% |
| Responsibility Alignment |      10% |
| Preferred Skills         |       7% |
| Keyword Coverage         |       7% |
| Project Relevance        |       7% |
| Achievements / Impact    |       7% |
| Seniority Alignment      |       6% |
| Education Alignment      |       5% |
| ATS Compatibility        |       5% |
| Clarity / Readability    |       5% |
| Overall Resume Quality   |       6% |
| **Total**                | **100%** |

The resulting score shall be normalized to 0–100.

### Important scoring principle

Missing **mandatory requirements** shall have a significantly greater effect than missing optional requirements.

For example:

```text
Required Kubernetes experience
             ↓
        Missing
             ↓
      Significant penalty
```

whereas:

```text
Preferred Redis experience
             ↓
        Missing
             ↓
       Small penalty
```

The scoring engine should therefore account for **requirement criticality**, rather than treating every keyword equally.

---

# 15. Score Presentation

The application shall display the overall score alongside category scores.

Example:

```text
ROLE READINESS

78 / 100

Required Skills       84
Experience             76
Responsibilities      81
Projects               72
Keywords               69
ATS                    93
Achievements            61
```

Each score shall be accompanied by an explanation.

---

# 16. AI Suggestions

### FR-AI-001

The system shall generate resume improvement recommendations using the LLM.

### FR-AI-002

Recommendations shall be grounded in:

* Resume content
* Job requirements
* Detected gaps
* Detected weaknesses

### FR-AI-003

Recommendations may include:

* Rewriting bullet points
* Reordering information
* Improving technical specificity
* Improving achievement descriptions
* Adding relevant existing skills
* Improving keyword coverage
* Improving project descriptions
* Improving summary sections
* Removing irrelevant information
* Improving clarity
* Improving ATS compatibility

---

# 17. Aggressive Optimization

RoleReady shall support aggressive optimization.

The optimizer may significantly rewrite and restructure resume content where appropriate.

However, information that is not present in the original resume or supplied by the user shall be treated as **unverified**.

The system shall not silently present unsupported information as fact.

---

# 18. AI Clarification Questions

This is a core RoleReady feature.

### FR-AI-020

After analysis, the LLM may identify information that could substantially improve the resume but is not available in the existing resume.

The system shall generate custom questions.

Example:

> The job description requires Docker experience, but your resume doesn't mention it.
>
> **Have you used Docker?**
>
> ○ Yes
> ○ No
> ○ I'm not sure

Another:

> Your project description doesn't mention performance improvements.
>
> **Did you measure response time, throughput, or another performance metric?**

---

# 19. User Confirmation

### FR-AI-030

The system shall ask the user for confirmation before incorporating potentially new information into the resume.

### FR-AI-031

The system shall allow users to provide additional information.

### FR-AI-032

The system shall distinguish:

```text
Existing evidence
        vs.
User-confirmed information
        vs.
LLM inference
```

### FR-AI-033

The system shall not silently convert an LLM inference into a factual resume claim.

---

# 20. Resume Optimization

The optimizer shall consider all resume sections.

### FR-OPT-001

The system shall optimize experience descriptions.

### FR-OPT-002

The system shall optimize project descriptions.

### FR-OPT-003

The system shall optimize skills.

### FR-OPT-004

The system shall optimize summary/objective content.

### FR-OPT-005

The system shall optimize achievements.

### FR-OPT-006

The system shall optimize certifications.

### FR-OPT-007

The system shall optimize section content and ordering where appropriate.

### FR-OPT-008

The system shall preserve user-provided formatting constraints.

---

# 21. Change Review

RoleReady shall provide a before/after comparison.

Example:

```text
BEFORE

Developed a web application using Node.js.


AFTER

Developed a Node.js REST API supporting
authentication and PostgreSQL persistence.

              [Accept] [Reject]
```

### FR-OPT-020

Users shall be able to review proposed modifications.

### FR-OPT-021

Users shall be able to accept individual modifications.

### FR-OPT-022

Users shall be able to reject individual modifications.

### FR-OPT-023

Users shall be able to review the final optimized resume before export.

---

# 22. Output Formats

## 22.1 PDF

### FR-OUT-001

RoleReady shall generate a downloadable PDF resume.

### FR-OUT-002

The generated PDF shall preserve the user's formatting/template as far as possible.

---

## 22.2 Markdown

### FR-OUT-010

If the input resume was Markdown, RoleReady shall provide Markdown output.

### FR-OUT-011

The output shall preserve the original Markdown structure as strictly as possible.

---

## 22.3 LaTeX

### FR-OUT-020

If the input resume was LaTeX, RoleReady shall provide LaTeX output.

### FR-OUT-021

The system shall preserve the original LaTeX template and structure as strictly as possible.

---

# 23. Data Privacy

Privacy is a fundamental requirement of RoleReady.

### FR-PRIV-001

Uploaded resumes shall not be permanently stored.

### FR-PRIV-002

Job descriptions shall not be permanently stored.

### FR-PRIV-003

Analysis results shall not be permanently stored.

### FR-PRIV-004

Optimized resumes shall not be permanently stored.

### FR-PRIV-005

Session resume data shall be deleted when the browser/tab session ends.

### FR-PRIV-006

The system shall not retain uploaded resume files after the session.

### FR-PRIV-007

The system shall delete extracted resume content associated with the session.

### FR-PRIV-008

The system shall not maintain resume history.

### FR-PRIV-009

The system shall not maintain job application history.

### FR-PRIV-010

User authentication/account information may persist independently of resume data.

---

# 24. LLM Integration

## 24.1 Model

RoleReady V1 shall use:

> **Gemini 3.8 Flash**

The model shall be accessed through the backend.

The browser shall not directly communicate with the LLM provider.

```text
Angular
   │
   ▼
Spring Boot
   │
   ▼
Gemini API
```

---

# 25. Structured LLM Responses

### FR-LLM-001

LLM requests shall request structured JSON responses.

### FR-LLM-002

Spring Boot shall validate LLM responses against expected schemas.

### FR-LLM-003

Malformed LLM responses shall not be blindly passed to the frontend.

### FR-LLM-004

The backend shall handle LLM failures gracefully.

---

# 26. Example Analysis Schema

A conceptual response shall resemble:

```json
{
  "overallAssessment": "...",
  "requirements": [
    {
      "requirement": "Java",
      "importance": "REQUIRED",
      "status": "STRONG_MATCH",
      "evidence": ["..."],
      "confidence": 0.94
    }
  ],
  "scores": {
    "requiredSkills": 84,
    "experience": 76,
    "responsibilities": 81,
    "keywords": 69
  },
  "missingEvidence": [
    {
      "requirement": "Docker",
      "reason": "No evidence found"
    }
  ],
  "suggestions": []
}
```

This is illustrative rather than a final API contract.

---

# 27. AI Service Architecture

The application should isolate LLM functionality behind an internal AI service abstraction.

```text
AnalysisService
       │
       ▼
    AiService
       │
       ├── Prompt construction
       ├── Gemini request
       ├── JSON parsing
       ├── Schema validation
       └── Error handling
```

The application should not spread direct Gemini API calls throughout business services.

---

# 28. Prompt Management

Prompts shall be treated as application components rather than hard-coded randomly throughout controllers.

The system should maintain separate logical prompts for:

* Resume extraction
* JD extraction
* Requirement matching
* Evaluation
* Question generation
* Resume optimization
* Resume formatting
* Validation

This enables independent iteration of each AI task.

---

# 29. AI Reliability

The system shall account for LLM uncertainty.

LLM-generated information shall have an associated confidence or classification where useful.

The application shall distinguish:

* Explicit evidence
* Strong inference
* Weak inference
* Missing information

The system shall avoid presenting uncertain inference as verified candidate experience.

---

# 30. Frontend Requirements

The frontend shall be implemented using Angular.

## 30.1 Design

RoleReady shall use a **simple, attractive neobrutalist design**.

The design should emphasize:

* Strong typography
* Clear borders
* High-contrast UI
* Distinct buttons
* Simple geometric surfaces
* Deliberate visual hierarchy
* Minimal unnecessary decoration

The interface should remain professional despite the neobrutalist aesthetic.

---

# 31. Main Screens

The application shall contain at least:

### 31.1 Landing Page

Should explain:

* What RoleReady does
* Resume analysis
* Job matching
* Resume optimization
* Free usage availability
* Call to action

### 31.2 Resume Workspace

Allows:

* Uploading a resume
* Creating a resume
* Editing resume content
* Reviewing parsed content

### 31.3 Job Description Workspace

Allows users to paste a JD.

### 31.4 Analysis Dashboard

Displays:

* Role Readiness score
* Category scores
* Strong matches
* Weak matches
* Missing requirements
* Suggestions

### 31.5 Questions

Displays dynamically generated clarification questions.

### 31.6 Optimization Workspace

Provides:

* Original content
* Proposed content
* Accept/reject controls
* Final resume preview

### 31.7 Export

Provides appropriate export options:

```text
Download PDF
Download Markdown
Download LaTeX
```

Only applicable formats shall be presented.

### 31.8 Account

Provides:

* Account information
* Change password
* Password reset
* Delete account
* Logout

---

# 32. Backend Architecture

The backend shall use Java and Spring Boot.

A logical architecture:

```text
┌─────────────────────────────────────────┐
│                Angular                  │
└────────────────────┬────────────────────┘
                     │ REST
┌────────────────────▼────────────────────┐
│              Spring Boot                │
│                                         │
│ Controllers                             │
│     ↓                                   │
│ Application Services                    │
│     ↓                                   │
│ Domain / Business Logic                 │
│     ↓                ↓                  │
│ Repositories       AI Service           │
└───────────┬──────────────┬──────────────┘
            │              │
            ▼              ▼
          MySQL       Gemini API
```

---

# 33. Suggested Spring Boot Modules

```text
auth
user
resume
job
analysis
optimization
ai
export
session
```

### Auth

Handles authentication and authorization.

### Resume

Handles:

* Upload
* Parsing
* Creation
* Editing
* Transformation

### Job

Handles JD processing.

### Analysis

Handles:

* Matching
* Scoring
* Evaluation

### Optimization

Handles AI suggestions and final resume generation.

### AI

Handles Gemini integration.

### Export

Handles PDF/Markdown/LaTeX generation.

### Session

Handles temporary data and lifecycle/deletion.

---

# 34. Database

MySQL shall be used for persistent application data.

Because resumes must not be permanently stored, the database shall **not function as a permanent resume repository**.

Persistent entities may include:

```text
User
Authentication
Account metadata
Usage information
```

Temporary session entities may include:

```text
ResumeSession
JobDescriptionSession
AnalysisSession
OptimizationSession
```

These temporary entities shall be deleted when the session ends.

---

# 35. Session Data Model

A conceptual relationship:

```text
User
 │
 └── Session
      │
      ├── Resume
      │
      ├── Job Description
      │
      ├── Analysis
      │
      ├── Questions
      │
      └── Optimized Resume
```

None of the child data shall survive session termination.

---

# 36. API Requirements

The backend shall expose REST APIs.

Potential API structure:

```text
POST   /api/auth/register
POST   /api/auth/login
POST   /api/auth/google
POST   /api/auth/verify-email
POST   /api/auth/forgot-password
POST   /api/auth/reset-password
POST   /api/auth/change-password
DELETE /api/auth/account

POST   /api/resume/upload
POST   /api/resume/create
GET    /api/resume/current
PUT    /api/resume/current

POST   /api/job-description/analyze

POST   /api/analysis
GET    /api/analysis/current

POST   /api/questions/answer

POST   /api/optimization
PUT    /api/optimization/changes/{id}

POST   /api/export/pdf
POST   /api/export/markdown
POST   /api/export/latex
```

These endpoints represent the logical API surface; exact endpoint design may be refined during implementation.

---

# 37. Security Requirements

### NFR-SEC-001

Passwords shall never be stored in plaintext.

### NFR-SEC-002

Passwords shall be stored using a modern password hashing algorithm.

### NFR-SEC-003

Authentication tokens/session credentials shall be protected from unauthorized access.

### NFR-SEC-004

Resume uploads shall be validated.

### NFR-SEC-005

The backend shall enforce authorization on authenticated resources.

### NFR-SEC-006

Users shall not be able to access another user's session data.

### NFR-SEC-007

LLM API credentials shall never be exposed to Angular/browser clients.

### NFR-SEC-008

Uploaded files shall be handled safely to prevent malicious file uploads.

---

# 38. Performance Requirements

Because V1 is intended for relatively low traffic, the system shall prioritize correctness and reliability over extreme horizontal scalability.

Nevertheless:

### NFR-PERF-001

Normal non-LLM API requests should respond quickly under expected V1 load.

### NFR-PERF-002

Long-running LLM operations shall provide an appropriate loading state.

### NFR-PERF-003

The frontend shall not freeze while waiting for LLM operations.

### NFR-PERF-004

Large resume uploads shall be bounded by configurable size limits.

### NFR-PERF-005

LLM requests shall use appropriate timeouts.

---

# 39. Reliability

### NFR-REL-001

LLM failures shall result in a user-friendly error.

### NFR-REL-002

Malformed LLM JSON shall be detected.

### NFR-REL-003

Temporary failures should be retryable where safe.

### NFR-REL-004

Partial failures shall not result in corrupted resume content.

### NFR-REL-005

The system shall not silently discard user edits.

---

# 40. Error Handling

The system shall handle:

* Invalid file type
* Corrupted file
* Unsupported resume structure
* Empty resume
* Empty JD
* Extremely large input
* LLM timeout
* LLM API failure
* Invalid LLM response
* Authentication failure
* Expired session
* Export failure
* Unsupported formatting

Errors should be presented in human-readable form.

---

# 41. Privacy Lifecycle

The intended lifecycle is:

```text
             User uploads resume
                     │
                     ▼
             Temporary storage
                     │
                     ▼
               Parse resume
                     │
                     ▼
              Analyze + edit
                     │
                     ▼
               Optimize
                     │
                     ▼
                 Export
                     │
                     ▼
             Browser/session ends
                     │
                     ▼
             Delete session data
```

Account data remains.

Resume/application data does not.

---

# 42. Session Termination

A session is considered terminated when the browser/tab session ends, according to the application's session-management mechanism.

The user **does not need to explicitly log out**.

The system shall not interpret logout as the sole trigger for data deletion.

Implementation shall ensure that temporary data cannot become permanently retained merely because the user closes the browser.

---

# 43. Usability Requirements

The user should be able to go from:

> "I have a resume and a job description"

to:

> "I have an optimized resume"

without needing to understand how the underlying LLM works.

The system should clearly communicate:

* What it is doing
* Why it is making a recommendation
* What information it needs
* Which information came from the user
* Which information is inferred
* What will change

---

# 44. Non-Functional UX Requirements

The application should:

* Be responsive
* Work on desktop and mobile browsers
* Provide clear loading states
* Avoid unnecessary multi-step navigation
* Preserve unsaved edits during normal navigation
* Clearly indicate destructive actions
* Make AI-generated changes reviewable

---

# 45. Accessibility

RoleReady should:

* Use semantic HTML
* Provide keyboard navigation
* Maintain adequate text contrast
* Provide labels for form controls
* Avoid conveying essential information through color alone
* Provide accessible feedback for asynchronous operations

---

# 46. Export Requirements

PDF generation shall produce a professionally formatted resume suitable for job applications.

Where the original format is Markdown or LaTeX, RoleReady shall provide the corresponding source format in addition to PDF.

The system shall not force Markdown/LaTeX users into a completely different proprietary representation.

---

# 47. Acceptance Criteria — Core Workflow

The MVP shall be considered functionally successful if a user can:

1. Open RoleReady.
2. Upload a PDF/DOCX/TXT/MD/LaTeX resume **or create one**.
3. Have the resume parsed into editable content.
4. Paste a job description.
5. Have the JD analyzed.
6. Receive a numerical Role Readiness score.
7. View category-level evaluation.
8. See matching requirements.
9. See missing/weak requirements.
10. Receive improvement suggestions.
11. Receive custom questions generated specifically from the detected gaps.
12. Answer those questions.
13. Generate an optimized resume.
14. Review changes.
15. Accept/reject changes.
16. Export the resulting resume as PDF.
17. Export Markdown/LaTeX when applicable.
18. Have session resume data deleted when the session ends.

---

# 48. Authentication Acceptance Criteria

### Guest

A new visitor shall be able to perform three complete workflows without authentication.

On attempting a fourth workflow:

```text
You've used your 3 free analyses.

Sign in to continue.

[ Continue with Google ]
[ Continue with Email ]
```

### Authenticated

An authenticated user shall be able to perform additional workflows without being restricted by the three-use guest limit.

---

# 49. AI Acceptance Criteria

The AI subsystem shall:

* Use Gemini 3.8 Flash.
* Return structured JSON.
* Have its responses validated.
* Identify resume/JD matches.
* Identify missing evidence.
* Generate evaluation scores/evidence.
* Generate improvement recommendations.
* Generate custom questions.
* Generate optimized resume content.
* Respect user-confirmed information.
* Avoid silently presenting unsupported information as verified fact.

---

# 50. Important Product Principle

RoleReady should **not simply optimize for keyword stuffing**.

For example, if the JD contains:

> Kubernetes

RoleReady should not automatically insert "Kubernetes" into the resume.

Instead:

```text
JD requires Kubernetes
          │
          ▼
Does resume contain evidence?
       /       \
     YES        NO
      │          │
      ▼          ▼
Optimize      Ask user
existing       whether
evidence       they have
               experience
                  │
             ┌────┴────┐
            YES        NO
             │          │
             ▼          ▼
       Incorporate    Don't claim
       information    experience
```

This distinction is central to the credibility of the product.

---

# 51. Proposed Technology Stack

## Frontend

**Angular**

Responsibilities:

* UI
* Resume editor
* Analysis visualization
* Change review
* Authentication UI
* File upload
* Export interaction

## Backend

**Java + Spring Boot**

Responsibilities:

* REST API
* Authentication
* Session management
* Business logic
* Scoring
* Resume/JD orchestration
* Gemini integration
* File processing
* Export generation

## Database

**MySQL**

Responsibilities:

* Persistent account data
* Authentication-related data
* Usage tracking
* Temporary session data where appropriate

## AI

**Gemini 3.8 Flash**

Responsibilities:

* Semantic extraction
* Requirement analysis
* Matching
* Evaluation
* Question generation
* Suggestions
* Optimization

---

# 52. Architectural Principle

The most important architectural separation should be:

```text
LLM
 │
 │ interprets
 ▼
Structured Evidence
 │
 │ consumed by
 ▼
Spring Boot
 │
 │ determines
 ▼
Business Logic / Score
 │
 ▼
Angular
```

Rather than:

```text
LLM
 │
 │ "I think this resume is 82/100"
 ▼
Angular
```

The former makes RoleReady's behavior more deterministic, testable, explainable, and replaceable.

---

# 53. Future Extensibility

Although not part of V1, the architecture should avoid preventing future functionality such as:

* Interview preparation
* Role-specific interview questions
* Skill-gap learning plans
* Cover-letter generation
* Job discovery
* Job application tracking
* Multiple job comparisons
* Resume quality benchmarking
* Career recommendations

The system should therefore use modular services rather than implementing the entire product as one resume-generation workflow.

---

# 54. V1 Success Definition

RoleReady V1 succeeds if it can reliably answer four questions for a job seeker:

### 1. "How well does my resume fit this job?"

**Role Readiness Score**

### 2. "Why?"

**Evidence-backed evaluation**

### 3. "What am I missing?"

**Requirements, skills, and evidence gaps**

### 4. "How can I make my resume better?"

**AI recommendations + user-confirmed optimization**

The final output should be a **role-specific, professionally formatted resume** while preserving the user's original template and source format wherever possible.