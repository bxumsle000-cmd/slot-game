
// 符號顯示對照表：資料層用英文名，畫面上顯示對應圖示
const SYMBOL_ICONS = {
    Blank: "",
    Cherry: "🍒",
    Lemon: "🍋",
    BAR: "BAR",
    Seven: "7️⃣",
    Wild: "🃏",
}

// 畫面上的 5 條 reel-strip（querySelectorAll 依 HTML 順序 = 左到右）
const STRIPS = document.querySelectorAll(".reel-strip")
const CELL_HEIGHT = 80    // 每格高度(px)，必須跟 CSS 的 .reel-cell height 一致，位移量以此為單位
const FILLER_COUNT = 24   // 轉動時墊在結果下方的填充符號數，越多「轉的距離」越長
const BASE_DURATION = 1.0 // 第 1 輪的動畫秒數
const STAGGER = 0.25      // 每輪比前一輪多轉的秒數 → 做出左到右依序停輪的節奏
const POOL= Object.keys(SYMBOL_ICONS)
const REEL_STRIPS = [POOL, POOL, POOL, POOL, POOL];  // 5 輪共用同一份

// 建立一格符號的 div（跟 HTML 裡的 .reel-cell 相同結構）
function buildCell(symbol) {
    const cell = document.createElement("div")
    cell.className = "reel-cell"
    cell.textContent = SYMBOL_ICONS[symbol]
    return cell
}
function renderSpin(grid) {
    const spins = [...STRIPS].map((strip, col) =>
        new Promise((resolve) => {
            // 1. 重建帶子內容：結果放最上面，下面墊填充符號
            strip.textContent = ""
            for (let row = 0; row < 3; row++) {
                strip.append(buildCell(grid[row][col]))
            }
            const pool = REEL_STRIPS[col]  // 填充符號從該輪實際的符號帶隨機抽，看起來才像真的在轉
            for (let i = 0; i < FILLER_COUNT; i++) {
                strip.append(buildCell(pool[Math.floor(Math.random() * pool.length)]))
            }

            // 2. 無動畫地跳到起點：往上位移 N 格，此刻視窗顯示的是填充區尾端
            strip.style.transition = "none"
            strip.style.transform = `translateY(-${FILLER_COUNT * CELL_HEIGHT}px)`  // transform: translateY(-1920px)

            // 3. 強制 reflow：逼瀏覽器先記住起點位置，
            //    否則起點和終點會被合併成同一次更新，直接跳到終點、沒有動畫
            strip.offsetHeight

            // 4. 打開 transition、滑回 0 → 帶子往下轉，結果從上方轉入視窗
            //    cubic-bezier 是 ease-out 曲線：先快後慢，模擬轉輪減速停下
            const duration = BASE_DURATION + col * STAGGER  // 越右邊的輪轉越久 → 依序停輪
            strip.style.transition = `transform ${duration}s`
            strip.style.transform = "translateY(0)"

            // 5. 這一輪的 transition 跑完（停輪）時 resolve
            strip.addEventListener("transitionend", () => {
                resolve()
            }, { once: true })
        }))
    return Promise.all(spins)
    // 等 5 輪全部停輪，整個動畫才算結束
}


const paytableDialog = document.getElementById("paytableDialog");
document.getElementById("paylineTable").addEventListener("click", () => paytableDialog.showModal());
document.querySelector("#closeRule").addEventListener("click", () => paytableDialog.close())