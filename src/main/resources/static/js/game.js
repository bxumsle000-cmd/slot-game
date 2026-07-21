// ============================================================
// 前端遊戲邏輯：呼叫後端 API，把結果寫回畫面
// 後端提供： GET /api/deposit  、 POST /api/spin
// ============================================================


// 常用元素先抓好
const depositEl = document.getElementById('deposit');
const winScoreEl = document.getElementById('winScore');
const betAmountEl = document.getElementById('betAmount');
const resultEl = document.getElementById('result');
const spinBtn = document.getElementById('spin');
const betBtn = document.getElementById('bet');

async function doSpin(){
    const bet = parseInt(betAmountEl.textContent);
    const res = await fetch(`api/spin?betAmount=${bet}`,{method : "post"});
    const  data = await res.json();
    return data;
}

spinBtn.addEventListener("click",async ()=>{
    betBtn.disabled = true;
    const data = await doSpin();
    await  renderSpin(data.outcome.grid);
    winScoreEl.textContent = data.win;
    depositEl.textContent = data.deposit;
    betBtn.disabled = false;
})



