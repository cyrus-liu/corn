FROM python:3.8.0
COPY ./distinguishapp.py ./app
COPY ./model/20221105-141453.hdf5 ./app
COPY ./requirements.txt ./app
WORKDIR /app
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
EXPOSE 8080
ENTRYPOINT ["python"]
CMD ["distinguishapp.py"]