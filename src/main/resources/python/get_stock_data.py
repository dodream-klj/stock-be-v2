import FinanceDataReader as fdr
import sys
import json

# 파라미터로 받은 종목 코드, 시작 날짜, 종료 날짜
stock_code = sys.argv[1]
start_date = sys.argv[2]
end_date = sys.argv[3]

# 주식 데이터 가져오기
df = fdr.DataReader(stock_code, start_date, end_date)

# JSON 형식으로 변환
json_data = df.reset_index().to_json(orient='records', date_format='iso')

# JSON 데이터 출력
print(json_data)