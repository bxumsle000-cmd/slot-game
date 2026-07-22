// 每次重置重整回初始金額
async function resetDeposit() {
    const res = await fetch("/api/reset", { method: "POST" });
    const data = await res.json();
    depositEl.textContent = data.deposit;
}
resetDeposit();

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
    let originalDeposit = parseInt(depositEl.textContent);
    let betAmount = parseInt(betAmountEl.textContent);
    if( originalDeposit< betAmount){
        resultEl.textContent ="餘額不足"
    }else {
    betBtn.disabled = true;
    const data = await doSpin();
    depositEl.textContent = data.afterBet;
    await renderSpin(data.outcome.grid);
    winScoreEl.textContent = data.win;
    depositEl.textContent = data.deposit;
    if(data.win>0){
        resultEl.textContent =`恭喜中獎: ${data.win}金幣`
    }else {
        resultEl.textContent ="再接再厲"
    }
    betBtn.disabled = false;
}}
    )





