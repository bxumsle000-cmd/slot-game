betBtn.addEventListener("click",()=>{
    let betAmount = parseInt(betAmountEl.textContent);
    if(betAmount<500){
        betAmount += 50;
    }else{
        betAmount = 50
    }
    document.getElementById('betAmount').textContent = betAmount;
})
