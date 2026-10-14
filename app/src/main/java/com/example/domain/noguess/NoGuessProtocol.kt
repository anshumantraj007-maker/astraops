package com.example.domain.noguess

data class EliminationQuestion(
    val id: String,
    val exam: String,
    val subject: String,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val trapIndex: Int, // The tempting trap option
    val trapReason: String,
    val deductionSteps: List<String>,
    val ruleApplied: String
)

object NoGuessDrillRepository {

    val SAMPLE_DRILLS = listOf(
        EliminationQuestion(
            id = "apfc_ir_code_1",
            exam = "UPSC APFC",
            subject = "Labour Laws",
            questionText = "Under the Industrial Relations Code, 2020, what is the statutory threshold of workers required in an industrial establishment to mandate the framing of Standing Orders?",
            options = listOf(
                "A) 100 or more workers",
                "B) 300 or more workers",
                "C) 500 or more workers",
                "D) 50 or more workers"
            ),
            correctIndex = 1, // B
            trapIndex = 0,     // A
            trapReason = "Option A (100 workers) was the rule under the Industrial Employment (Standing Orders) Act, 1946. UPSC deliberately tests outdated memory.",
            deductionSteps = listOf(
                "Step 1: Check baseline memory flag - 2020 Code consolidated and raised worker ease thresholds.",
                "Step 2: Eliminate Option D (50 is for health/safety canteen thresholds, not standing orders).",
                "Step 3: Eliminate Option C (500 is arbitrarily large; no labor code sets 500).",
                "Step 4: Binary Decision between 100 (Old) vs 300 (New). Deduce: 300 was the heavily debated new threshold. Confirm Option B."
            ),
            ruleApplied = "Legislative Delta Rule: Always replace pre-2020 threshold with Labor Code 2020 threshold (300)."
        ),

        EliminationQuestion(
            id = "apfc_epfo_split_2",
            exam = "UPSC APFC",
            subject = "Social Security",
            questionText = "In respect of an employee covered under the Employees' Provident Funds and Miscellaneous Provisions Act, 1952, what portion of the employer's 12% total contribution is credited into the Employees' Pension Scheme (EPS), 1995?",
            options = listOf(
                "A) 3.67% of wages",
                "B) 8.33% of wages",
                "C) 10.00% of wages",
                "D) Entire 12.00% of wages"
            ),
            correctIndex = 1, // B
            trapIndex = 0,     // A
            trapReason = "Option A (3.67%) is the remainder credited into EPF, NOT the Pension Scheme (EPS)!",
            deductionSteps = listOf(
                "Step 1: Total employer contribution is 12%. Total must sum to 12% (8.33 + 3.67 = 12.00).",
                "Step 2: Eliminate C and D immediately (Pension share is never 10% or 12%).",
                "Step 3: Recognize the binary pair: 8.33% vs 3.67%.",
                "Step 4: Statutory mapping: Pension (EPS) gets the major chunk (8.33%); Provident Fund gets the balance (3.67%). Select B."
            ),
            ruleApplied = "Binary Pair Split: In a 12% total, Major Share (8.33%) = Pension, Minor Share (3.67%) = EPF."
        ),

        EliminationQuestion(
            id = "apfc_accounting_expenditure_3",
            exam = "UPSC APFC",
            subject = "General Accounting",
            questionText = "Amount of Rs. 45,000 paid to technicians for the cartage and installation of a newly acquired automated milling machine should be debited to which account?",
            options = listOf(
                "A) Wages & Cartage Account",
                "B) Profit & Loss Account",
                "C) Machinery Account",
                "D) Miscellaneous Administrative Expenses Account"
            ),
            correctIndex = 2, // C
            trapIndex = 0,     // A
            trapReason = "Layman intuition debits wages to Wages Account. But in Accounting syntax, costs incurred to bring an asset to working condition MUST be capitalized.",
            deductionSteps = listOf(
                "Step 1: Check Accounting Principle: Capital vs Revenue expenditure.",
                "Step 2: Test condition: Is the asset already operational? No, installation is required to put it into first working use.",
                "Step 3: Apply Capitalization invariant: All acquisition/installation costs are added to asset cost.",
                "Step 4: Eliminate A, B, and D immediately. Select Machinery Account (C)."
            ),
            ruleApplied = "Capitalization Invariant: Initial commissioning and installation costs merge into Asset Account."
        ),

        EliminationQuestion(
            id = "ro_aro_hindi_tatsam_4",
            exam = "Allahabad HC RO/ARO",
            subject = "General Hindi",
            questionText = "निम्नलिखित में से कौन-सा शब्द 'तत्सम' (Tatsam) है?",
            options = listOf(
                "A) आँख",
                "B) किसान",
                "C) क्षेत्र",
                "D) बारात"
            ),
            correctIndex = 2, // C
            trapIndex = 1,     // B
            trapReason = "किसान feels serious, but it is Tadbhav of 'कृषक'.",
            deductionSteps = listOf(
                "Step 1: Apply Chandrabindu rule: 'आँख' has (ँ) -> 100% Tadbhav. Eliminate A.",
                "Step 2: Apply 'व' vs 'ब' rule: 'बारात' has 'ब' -> Tadbhav (Tatsam is वरयात्रा). Eliminate D.",
                "Step 3: Analyze 'क्षेत्र' vs 'किसान': 'क्षेत्र' has conjunct 'क्ष' and 'त्र' (classical Sanskrit roots).",
                "Step 4: 'क्षेत्र' transforms to 'खेत' in Tadbhav. Select C with 100% mathematical certainty."
            ),
            ruleApplied = "Phonetic Regex: Conjunct consonants (क्ष, त्र, ज्ञ, श्र) identify Tatsam with near 100% accuracy."
        )
    )
}
