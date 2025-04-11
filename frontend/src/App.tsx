import React from 'react';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import GoldRecordPage from './pages/GoldRecordPage';
import './App.css';

const App: React.FC = () => {
  return (
    <ConfigProvider
        locale={zhCN}
    >
      <GoldRecordPage />
    </ConfigProvider>
  );
};

export default App;
