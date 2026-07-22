
// 符號顯示對照表：資料層用英文名，畫面上顯示對應圖示
const SYMBOL_ICONS = {
    Blank: "",
    Cherry: "🍒",
    Lemon: "🍋",
    BAR: "BAR",
    Seven: "7️⃣",
    Wild: "⭐",
}

// 畫面上的 5 條 reel-strip（querySelectorAll 依 HTML 順序 = 左到右）
const STRIPS = document.querySelectorAll(".reel-strip")
const CELL_HEIGHT = 80    // 每格高度(px)，必須跟 CSS 的 .reel-cell height 一致，位移量以此為單位
const FILLER_COUNT = 24   // 轉動時墊在結果下方的填充符號數，越多「轉的距離」越長
const BASE_DURATION = 1.0 // 第 1 輪的動畫秒數
const STAGGER = 0.25      // 每輪比前一輪多轉的秒數 → 做出左到右依序停輪的節奏
const pool= Object.keys(SYMBOL_ICONS)

// 建立一格符號的 div（跟 HTML 裡的 .reel-cell 相同結構）
function buildCell(symbol) {
    const cell = document.createElement("div")
    cell.className = "reel-cell"
    cell.textContent = SYMBOL_ICONS[symbol]
    return cell
}
function renderSpin(grid) {
    const spins = [...STRIPS].map((strip,col)=>
    new Promise((resolve) => {
        strip.textContent = ""
        for(let row=0;row<3;row++){
            strip.append(buildCell(grid[row][col]))
        }
        for(let i=0; i<FILLER_COUNT;i++){
            let randomFiller = buildCell(pool[Math.floor(Math.random() * pool.length)] );
            strip.append(randomFiller); }
        strip.style.transition = "none" ;
        strip.style.transform =  `translateY(-${CELL_HEIGHT*FILLER_COUNT}px)`

        strip.offsetHeight

        const duration = BASE_DURATION + STAGGER*col
        strip.style.transition = `transform ${duration}s` ;
        strip.style.transform = "translateY(0)";

        strip.addEventListener("transitionend",()=>{
            resolve()
        },{once:true})
    }))
    return Promise.all(spins)
}

paytableDialog = document.querySelector("#paytableDialog")
paylineTable = document.querySelector("#paylineTable")
closeRule = document.querySelector("#closeRule")

paylineTable.addEventListener("click",()=> {paytableDialog.showModal()})
closeRule.addEventListener("click",()=> {paytableDialog.close()})