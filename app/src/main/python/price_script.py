import yfinance as yf

def getprice(args):

    #arrpy=["TWL.NS","RPOWER.NS","JPPOWER.NS","DFMFOODS.NS","JPPOWER.NS"]
    price_arr=[]
    data = yf.download(tickers=args, period='0d', interval='1d')
    for i in data['Close']:
        price_arr.append(i)
    return price_arr[0]

