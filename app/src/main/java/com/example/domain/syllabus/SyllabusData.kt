package com.example.domain.syllabus

data class LogicTreeNode(
    val condition: String,
    val result: String,
    val exception: String? = null,
    val pyqTrap: String? = null
)

data class CodeSyntaxModule(
    val id: String,
    val title: String,
    val exam: String,
    val subject: String,
    val pseudoCode: String,
    val logicFlowchart: List<LogicTreeNode>,
    val highYieldPyqInsight: String,
    val executionFormula: String
)

object SyllabusRepository {

    val MODULES = listOf(
        CodeSyntaxModule(
            id = "ir_code_standing_orders",
            title = "Industrial Relations Code 2020: Standing Orders & Disputes",
            exam = "UPSC APFC",
            subject = "Labour Laws & Industrial Relations",
            pseudoCode = """
// IRC 2020: Standing Orders & Retrenchment Schema
val ESTABLISHMENT_WORKERS: Int = currentWorkerCount()

fun checkStandingOrdersCompliance(workers: Int): ComplianceState {
    return if (workers >= 300) { // Threshold raised from 100 to 300!
        ComplianceState.MANDATORY_STANDING_ORDERS
    } else {
        ComplianceState.EXEMPT
    }
}

fun validateLayoffClosurePermission(workers: Int): Boolean {
    // Government prior permission required ONLY if threshold >= 300
    if (workers >= 300) {
        return requirePriorGovtPermission() // High Yield APFC Trap!
    }
    return requireNoticeOnly()
}

fun formGrievanceCommittee(workers: Int): GrievanceRedressalCommittee? {
    if (workers >= 20) {
        return GrievanceRedressalCommittee(
            maxMembers = 10,
            adequateWomenRepresentation = true
        )
    }
    return null
}
            """.trimIndent(),
            logicFlowchart = listOf(
                LogicTreeNode(
                    condition = "Establishment Worker Count >= 300",
                    result = "Mandatory Standing Orders certification + Prior Govt permission for Layoff/Retrenchment/Closure",
                    exception = "Appropriate Govt can exempt by notification",
                    pyqTrap = "TRAP: Old Act (1946) threshold was 100. New Code 2020 raised it to 300!"
                ),
                LogicTreeNode(
                    condition = "Establishment Worker Count >= 20",
                    result = "Mandatory Grievance Redressal Committee setup",
                    exception = "Maximum committee members capped at 10",
                    pyqTrap = "TRAP: Equal representation of women is statutory."
                ),
                LogicTreeNode(
                    condition = "Strike Notice Requirement",
                    result = "60 days advance notice + No strike within 14 days of giving notice",
                    exception = "Applies to ALL industrial establishments (not just public utilities anymore)",
                    pyqTrap = "TRAP: UPSC loves testing the 14-day embargo and conciliation period ban."
                )
            ),
            highYieldPyqInsight = "UPSC APFC questions test the delta between the 1947 ID Act / 1946 Standing Orders and the 2020 Labor Codes. Memorize the 300 worker threshold and 60/14 strike timeline.",
            executionFormula = "Workers >= 300 -> Govt Permission | Strike Notice: 60d max, 14d embargo | Grievance: >= 20 workers, max 10 members."
        ),

        CodeSyntaxModule(
            id = "social_security_epfo",
            title = "Social Security Code 2020: EPFO, EPS 1995 & Gratuity Engine",
            exam = "UPSC APFC",
            subject = "Social Security in India",
            pseudoCode = """
// EPFO & Gratuity Calculation Engine
val STATUTORY_WAGE_CEILING = 15000 // INR per month

fun calculateEpfoSplits(basicWage: Double): EpfContribution {
    val effectiveWage = minOf(basicWage, STATUTORY_WAGE_CEILING.toDouble())
    val employeePf = effectiveWage * 0.12 // 12% directly to EPF
    
    // Employer 12% total is bifurcated:
    val employerEps = effectiveWage * 0.0833 // 8.33% to Pension (EPS 1995)
    val employerEpf = effectiveWage * 0.0367 // 3.67% to Provident Fund
    val edli = effectiveWage * 0.005          // 0.5% Deposit Linked Insurance
    
    return EpfContribution(employeePf, employerEps, employerEpf, edli)
}

fun calculateGratuityEligibility(employmentType: EmploymentType, tenureYears: Double): Boolean {
    return when (employmentType) {
        EmploymentType.PERMANENT -> tenureYears >= 5.0 // 5 continuous years
        EmploymentType.FIXED_TERM -> tenureYears >= 1.0 // FIXED TERM: 1 year pro-rata!
        EmploymentType.WORKING_JOURNALIST -> tenureYears >= 3.0 // 3 years
    }
}
            """.trimIndent(),
            logicFlowchart = listOf(
                LogicTreeNode(
                    condition = "Employer 12% Contribution Bifurcation",
                    result = "8.33% -> EPS (Pension Scheme) | 3.67% -> EPF (Provident Fund) | 0.5% -> EDLI",
                    exception = "Central Govt contributes 1.16% towards EPS on wage ceiling",
                    pyqTrap = "TRAP: Employee's entire 12% goes to EPF, 0% of employee share goes to EPS."
                ),
                LogicTreeNode(
                    condition = "Gratuity for Fixed-Term Employees (Social Security Code 2020)",
                    result = "Eligible on pro-rata basis for 1 year of service",
                    exception = "Regular employees still require 5 continuous years",
                    pyqTrap = "TRAP: Questions often ask if fixed-term employees are exempt. They are NOT exempt!"
                ),
                LogicTreeNode(
                    condition = "Gig & Platform Workers Social Security",
                    result = "Aggregators must contribute 1% to 2% of annual turnover (capped at 5% of amount paid to workers)",
                    exception = "National Social Security Board established for unorganized/gig workers",
                    pyqTrap = "TRAP: Aggregators cannot escape contribution; it is statutory under 2020 Code."
                )
            ),
            highYieldPyqInsight = "EPFO calculations (12% employee, 8.33% + 3.67% employer, 0.5% EDLI) are 100% guaranteed questions in UPSC APFC / EO-AO.",
            executionFormula = "Employee 12% EPF | Employer: 8.33% EPS + 3.67% EPF + 0.5% EDLI | Fixed Term Gratuity: 1 Year Pro-rata."
        ),

        CodeSyntaxModule(
            id = "accounting_principles_engine",
            title = "General Accounting Principles: State Machine & Invariants",
            exam = "UPSC APFC",
            subject = "General Accounting Principles",
            pseudoCode = """
// Accounting Invariant Equation
// Assets = Liabilities + Capital (Owner's Equity)
data class BalanceSheet(var assets: Double, var liabilities: Double, var equity: Double) {
    fun verifyInvariant() = assets == (liabilities + equity)
}

// Golden Rules as Functional Dispatcher
fun applyJournalEntry(accountType: AccountType, transaction: Transaction): Entry {
    return when (accountType) {
        AccountType.REAL -> {
            // Rule: Debit what comes in, Credit what goes out
            if (transaction.isIncoming) Entry.DEBIT else Entry.CREDIT
        }
        AccountType.PERSONAL -> {
            // Rule: Debit the receiver, Credit the giver
            if (transaction.isReceiver) Entry.DEBIT else Entry.CREDIT
        }
        AccountType.NOMINAL -> {
            // Rule: Debit all expenses/losses, Credit all incomes/gains
            if (transaction.isExpenseOrLoss) Entry.DEBIT else Entry.CREDIT
        }
    }
}
            """.trimIndent(),
            logicFlowchart = listOf(
                LogicTreeNode(
                    condition = "Prudence / Conservatism Principle",
                    result = "Anticipate no future profits, but provide for all possible losses (e.g. Provision for Bad Debts)",
                    exception = "Do not create hidden reserves",
                    pyqTrap = "TRAP: Valuing inventory at 'Cost or Net Realizable Value, whichever is lower' stems directly from Prudence!"
                ),
                LogicTreeNode(
                    condition = "Capital vs Revenue Expenditure",
                    result = "Expenditure extending asset lifespan / earning capacity = Capital. Regular upkeep = Revenue.",
                    exception = "Legal expenses for acquiring land = Capital Expenditure",
                    pyqTrap = "TRAP: Wages paid for installation of new machinery = DEBITED to Machinery Account (Capital), NOT Wages Account!"
                ),
                LogicTreeNode(
                    condition = "Trial Balance Reconciliation",
                    result = "Compensating errors and Errors of Principle do NOT affect Trial Balance agreement",
                    exception = "Errors of Omission: Partial affects, Complete does not affect",
                    pyqTrap = "TRAP: Trial balance agreement is only prima facie proof of arithmetical accuracy, not conclusive proof!"
                )
            ),
            highYieldPyqInsight = "Treat Accounting like ACID database transactions. If machine installation wage is debited to wages, it violates the Principle of Capitalization.",
            executionFormula = "Assets = Liab + Equity | Prudence: Value at lower of cost or NRV | Machine installation = Capitalize!"
        ),

        CodeSyntaxModule(
            id = "ro_aro_computer_mastery",
            title = "HC RO/ARO: Computer & IT Architecture (BCA High Leverage)",
            exam = "Allahabad HC RO/ARO",
            subject = "Computer Knowledge & IT",
            pseudoCode = """
// Networking & Cyber Security Logic Table
enum class OsiLayer(val layerNumber: Int, val pdu: String, val devices: List<String>) {
    APPLICATION(7, "Data", listOf("Gateway")),
    PRESENTATION(6, "Data", listOf("SSL/TLS Encryption")),
    SESSION(5, "Data", listOf("Sockets, RPC")),
    TRANSPORT(4, "Segment", listOf("TCP, UDP")),
    NETWORK(3, "Packet", listOf("Router, IP, ICMP")),
    DATA_LINK(2, "Frame", listOf("Switch, Bridge, MAC")),
    PHYSICAL(1, "Bit", listOf("Hub, Repeater, Cables"))
}

fun calculateSubnetHosts(cidrMask: Int): Int {
    val hostBits = 32 - cidrMask
    return (1 shl hostBits) - 2 // Minus Network ID and Broadcast ID
}
            """.trimIndent(),
            logicFlowchart = listOf(
                LogicTreeNode(
                    condition = "OSI Layer 2 vs Layer 3 Switching",
                    result = "Layer 2 operates on MAC addresses (Frames); Layer 3 operates on IP addresses (Packets)",
                    exception = "Brouters work on both Layer 2 and 3",
                    pyqTrap = "TRAP: Hub is Layer 1 (Physical, Broadcasts to all); Switch is Layer 2 (Unicast via MAC table)."
                ),
                LogicTreeNode(
                    condition = "Allahabad HC RO/ARO MS Office Shortcuts",
                    result = "Ctrl+Shift+= (Superscript) | Ctrl+= (Subscript) | Shift+F3 (Toggle Case) | F4 (Repeat Last Action)",
                    exception = "Ctrl+Enter inserts Page Break; Shift+Enter inserts Line Break",
                    pyqTrap = "TRAP: High repetition in RO/ARO exams. Test exact function key combinations."
                ),
                LogicTreeNode(
                    condition = "Cyber Law / IT Act 2000",
                    result = "Section 66A struck down (Shreya Singhal v UOI). Section 43 deals with penalty for damage to computer system.",
                    exception = "Digital Signatures defined under Section 2(p) using asymmetric crypto",
                    pyqTrap = "TRAP: Know the Section 66 variants (66C Identity theft, 66D Cheating by personation)."
                )
            ),
            highYieldPyqInsight = "Your BCA background gives you an insurmountable competitive edge here. Lock in 100% marks in Computer section in RO/ARO in under 8 minutes.",
            executionFormula = "OSI: 7-App, 4-TCP/UDP, 3-Router/Packet, 2-Switch/MAC | MS Word: Shift+F3 Case, Ctrl+Enter Page Break."
        ),

        CodeSyntaxModule(
            id = "ro_aro_hindi_grammar_syntax",
            title = "HC RO/ARO: General Hindi Phonetic Transformation Syntax",
            exam = "Allahabad HC RO/ARO",
            subject = "General Hindi",
            pseudoCode = """
// Tatsam -> Tadbhav Transformation Rules
fun transformTatsamToTadbhav(tatsamChar: Char): Char {
    return when (tatsamChar) {
        'क्ष' -> 'ख' // e.g., क्षीर -> खीर, क्षेत्र -> खेत, अक्षत -> आखत
        'श्र' -> 'स' // e.g., श्रावण -> सावन, शृंगार -> सिंगार
        'व'  -> 'ब' // e.g., विद्युत -> बिजली, वक -> बगुला, विवाह -> ब्याह
        'ष'  -> 'स' // e.g., षष्ठ -> छठा, कृषक -> किसान
        else -> tatsamChar
    }
}
            """.trimIndent(),
            logicFlowchart = listOf(
                LogicTreeNode(
                    condition = "Rule 1: 'क्ष' phoneme transformation",
                    result = "Tatsam containing 'क्ष' converts into 'ख' or 'छ' in Tadbhav (अक्षि -> आँख, परीक्षा -> परख)",
                    exception = "Some rare regional dialects retain friction",
                    pyqTrap = "TRAP: Look at the conjunct consonant; if 'क्ष', it is 99% Tatsam."
                ),
                LogicTreeNode(
                    condition = "Rule 2: 'व' into 'ब'",
                    result = "Tatsam words with 'व' become 'ब' in common speech (वरयात्रा -> बारात, वानर -> बंदर)",
                    exception = "Words with Sanskrit 'ब' are few (e.g. बन्धु -> भाई)",
                    pyqTrap = "TRAP: शुद्ध वर्तनी tests: 'बारात' is Tadbhav; 'वरयात्रा' is Tatsam."
                ),
                LogicTreeNode(
                    condition = "Rule 3: Chandrabindu (ँ) vs Anusvara (ं)",
                    result = "Words with Chandrabindu (ँ) are ALMOST ALWAYS Tadbhav (उदा. चाँद, आँख, मुँह)",
                    exception = "Tatsam utilizes Anusvara or classical Sanskrit nasal (दन्त -> दाँत)",
                    pyqTrap = "TRAP: Immediate elimination shortcut in RO/ARO Hindi paper!"
                )
            ),
            highYieldPyqInsight = "Hindi grammar for RO/ARO is strictly algorithmic. Use phonetic regex rules rather than reading literature.",
            executionFormula = "क्ष -> ख/छ | व -> ब | ऋ/ष/श्र -> Tatsam | Chandrabindu (ँ) -> 100% Tadbhav."
        )
    )
}
