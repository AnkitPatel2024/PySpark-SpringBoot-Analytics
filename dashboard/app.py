import streamlit as st
import pandas as pd
import plotly.express as px
import requests

BASE_URL = "http://localhost:8080"

st.set_page_config(
    page_title="Banking Analytics Dashboard",
    layout="wide"
)

st.title("📊 Commercial & Investment Banking Analytics")

# -----------------------
# Sidebar
# -----------------------
st.sidebar.header("Filters")

rollup_type = st.sidebar.selectbox(
    "Rollup Type",
    ["Daily", "Monthly"]
)

def fetch_data(endpoint, params=None):
    try:
        response = requests.get(f"{BASE_URL}{endpoint}", params=params)
        if response.status_code == 404:
            return [] # Return empty list if no data found
        response.raise_for_status()
        return response.json()
    except Exception as e:
        st.error(f"Connection Error: {e}")
        return []

# -----------------------
# DAILY
# -----------------------
if rollup_type == "Daily":
    st.subheader("📅 Daily Transaction Rollups")

    selected_date = st.sidebar.date_input("Transaction Date")

    data = fetch_data(
        "/daily-rollups",
        params={"date": selected_date.isoformat()}
    )

    if not data:
        st.info("No data available.")
    else:
        df = pd.DataFrame(data)


        df["totalAmount"] = df["totalAmount"].astype(float)
        df["txnCount"] = df["txnCount"].astype(int)

        df = df.rename(columns={
            "branch": "Branch Name",
            "productType": "Product Category",
            "totalAmount": "Total Volume",
            "txnCount": "Transaction Volume",
            "currency": "Currency",
            "txnDate": "Transaction Date"
            
        })
        
        st.dataframe(df, use_container_width=True)

        col1, col2 = st.columns(2)

        with col1:
            fig_amount = px.bar(
                df,
                x="Branch Name",
                y="Total Volume" ,
                color="Product Category",
                title="Total Amount by Branch",
                labels={"Total Volume": "Amount"},
                text_auto=".2s"
            )
            st.plotly_chart(fig_amount, use_container_width=True)

        with col2:
            fig_count = px.bar(
                df,
                x="Branch Name",
                y="Transaction Volume",
                color="Product Category",
                title="Transaction Count by Branch"
            )
            st.plotly_chart(fig_count, use_container_width=True)

# -----------------------
# MONTHLY
# -----------------------
else:
    st.subheader("🗓 Monthly Transaction Rollups")

    selected_month = st.sidebar.text_input(
        "Year-Month (YYYY-MM)",
        value="2024-01"
    )

    data = fetch_data(
        "/monthly-rollups",
        params={"yearMonth": selected_month}
    )

    if not data:
        st.info("No data available.")
    else:
        df = pd.DataFrame(data)

        df["totalAmount"] = df["totalAmount"].astype(float)
        df["txnCount"] = df["txnCount"].astype(int)

        st.dataframe(df, use_container_width=True)

        col1, col2 = st.columns(2)

        with col1:
            fig_amount = px.bar(
                df,
                x="branch",
                y="totalAmount",
                color="productType",
                title="Monthly Amount by Branch",
                text_auto=".2s"
            )
            st.plotly_chart(fig_amount, use_container_width=True)

        with col2:
            fig_count = px.bar(
                df,
                x="branch",
                y="txnCount",
                color="productType",
                title="Monthly Transaction Count"
            )
            st.plotly_chart(fig_count, use_container_width=True)

st.markdown("---")
st.caption("Spring Boot API • PostgreSQL • PySpark / Databricks • Plotly Dashboard")