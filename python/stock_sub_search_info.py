import FinanceDataReader as fdr
import os
import sys
import json

# 종목명 검색 키워드 입력받기
stock_name_keyword = sys.argv[1]

# KRX 종목 코드 불러오기
krx = fdr.StockListing('KRX')

# 종목명에서 키워드 검색 (대소문자 구분 없이)
filtered_stocks = krx[krx['Name'].str.contains(stock_name_keyword, case=False, na=False)]

# 검색된 결과를 JSON 형식으로 변환
filtered_stocks_json = filtered_stocks.to_json(orient='records', force_ascii=False)

# JSON 배열 출력
parsed_json = json.loads(filtered_stocks_json)  # JSON 문자열을 파이썬 객체로 변환
if parsed_json:  # 데이터가 존재할 경우
    print(filtered_stocks_json)  # 전체 항목 출력
else:
    print(json.dumps({"error": f"'{stock_name_keyword}'에 대한 검색 결과가 없습니다."}, ensure_ascii=False, indent=4))
