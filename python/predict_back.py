# 필요한 라이브러리 임포트
import FinanceDataReader as fdr
import numpy as np
import matplotlib.pyplot as plt
from tensorflow.keras import Sequential
from tensorflow.keras.layers import Dense, LSTM, Dropout
from datetime import datetime
from tensorflow.keras.models import load_model  # 모델 로드 기능 추가
import os  # 경로 확인 및 디렉터리 생성용
import time  # 파일의 수정 시간 확인용
from datetime import timedelta
import argparse
import warnings
import os




# Min-Max 스케일러 함수 정의
def min_max_scaler(data):
    """최솟값과 최댓값을 이용하여 0 ~ 1 값으로 변환"""
    numerator = data - np.min(data, 0)
    denominator = np.max(data, 0) - np.min(data, 0)
    return numerator / (denominator + 1e-7)


# 데이터 불러오기 함수
def load_data(stock_code, start_date, end_date):
    """주식 데이터를 불러와서 스케일링 처리 후 반환"""
    df = fdr.DataReader(stock_code, start_date, end_date)
    dfx = df[["Open", "High", "Low", "Volume", "Close"]]
    yesterday_close = df.Close[-1]  # 전일 종가

    dfx = min_max_scaler(dfx)  # 데이터 스케일링
    dfy = dfx[["Close"]]  # 목표 변수 (종가)
    dfx = dfx[["Open", "High", "Low", "Volume"]]  # 입력 변수 (시가, 고가, 저가, 거래량)

    return dfx, dfy, df, yesterday_close


# 시계열 데이터 생성 함수
def create_timeseries_data(X, y, window_size=10):
    """슬라이딩 윈도우 기법으로 시계열 데이터 생성"""
    data_X, data_y = [], []
    for i in range(len(y) - window_size):
        _X = X[i : i + window_size]
        _y = y[i + window_size]
        data_X.append(_X)
        data_y.append(_y)
    return data_X, data_y


# LSTM 모델 정의 함수
def build_lstm_model(input_shape):
    """LSTM 모델을 생성하고 컴파일"""
    model = Sequential()
    model.add(
        LSTM(
            units=20, activation="relu", return_sequences=True, input_shape=input_shape
        )
    )
    model.add(Dropout(0.1))
    model.add(LSTM(units=20, activation="relu"))
    model.add(Dropout(0.1))
    model.add(Dense(units=1))
    model.compile(optimizer="adam", loss="mean_squared_error")
    return model


# 모델 훈련 함수
def train_model(model, train_X, train_y, epochs=70, batch_size=30):
    """모델을 훈련시키는 함수"""
    model.fit(train_X, train_y, epochs=epochs, batch_size=batch_size)


# 예측 함수
def make_predictions(model, test_X):
    """테스트 데이터를 이용해 예측값을 반환"""
    return model.predict(test_X)


# 결과 시각화 함수
def plot_predictions(test_y, pred_y):
    """실제 값과 예측 값을 시각화"""
    plt.figure()
    plt.plot(test_y, color="red", label="real SEC stock price")
    plt.plot(pred_y, color="blue", label="predicted SEC stock price")
    plt.title("SEC stock price prediction")
    plt.xlabel("time")
    plt.ylabel("stock price")
    plt.legend()
    plt.show()


import os  # 경로 확인 및 디렉터리 생성용
from datetime import datetime  # datetime 모듈 추가


# 파일의 수정 시간을 확인하고 오늘 생성되었는지 판단하는 함수
def is_file_from_today(file_path):
    """파일의 마지막 수정 시간이 오늘인지 확인"""
    if not os.path.exists(file_path):
        return False  # 파일이 없으면 False 반환

    file_mod_time = os.path.getmtime(file_path)  # 마지막 수정 시간 (timestamp)
    today = datetime.today().date()  # 오늘 날짜
    file_date = datetime.fromtimestamp(file_mod_time).date()  # 파일의 날짜

    # 파일 수정일이 오늘인지 확인
    return file_date == today


# main 함수 수정
def main(stock_code):
    # 주식 데이터 불러오기
    start_date = "2000-05-04"
    end_date = datetime.today().strftime("%Y-%m-%d")  # 오늘 날짜
    model_file = f"model/{stock_code}.h5"  # 모델 파일 경로

    dfx, dfy, df, yesterday_close = load_data(stock_code, start_date, end_date)

    # 시계열 데이터 준비
    X = dfx.values.tolist()
    y = dfy.values.tolist()
    window_size = 10
    data_X, data_y = create_timeseries_data(X, y, window_size)

    # 훈련 데이터와 테스트 데이터 분리
    train_size = int(len(data_y) * 0.7)
    train_X = np.array(data_X[0:train_size])
    train_y = np.array(data_y[0:train_size])

    test_size = len(data_y) - train_size
    test_X = np.array(data_X[train_size : len(data_X)])
    test_y = np.array(data_y[train_size : len(data_y)])

    # 모델 파일 존재 여부 및 수정 날짜 확인
    if not os.path.exists("model"):
        os.makedirs("model")  # 디렉터리가 없으면 생성

    if not os.path.exists(model_file) or not is_file_from_today(model_file):
        model = build_lstm_model(input_shape=(window_size, 4))
        train_model(model, train_X, train_y)
        model.save(model_file)
    else:
        model = load_model(model_file)

    # 예측
    pred_y = make_predictions(model, test_X)

    tomorrow_price = int(df.Close.iloc[-1] * pred_y[-1] / dfy.Close.iloc[-1])

    # 내일 주식 가격 예측 및 전일 종가 출력
    if tomorrow_price > yesterday_close:
        print(f"내일 SEC 주가: {tomorrow_price} KRW (상승)")
        print(f"전일 종가: {yesterday_close} KRW")
    else:
        print(f"전일 종가: {yesterday_close} KRW")
        print("내일 주가는 어제보다 높지 않습니다.")


if __name__ == "__main__":
    # TensorFlow 로그 숨기기
    os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'  # 모든 TensorFlow 로그 숨기기

    # 경고 숨기기
    warnings.simplefilter(action='ignore', category=FutureWarning)
    warnings.simplefilter(action='ignore', category=DeprecationWarning)
    # argparse로 파라미터 처리
    parser = argparse.ArgumentParser(description="LSTM 주식 예측 모델 실행")
    parser.add_argument("stock_code", type=str, help="종목 코드 (예: '005930')")
    args = parser.parse_args()

    # main 함수 호출
    main(args.stock_code)
