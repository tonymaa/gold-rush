import React, { useState, useEffect } from 'react';
import { Layout, Card, Button, Modal, message, Typography, Space, List, Tag } from 'antd';
import { PlusOutlined, FilterOutlined, SortDescendingOutlined } from '@ant-design/icons';
import GoldRecordForm from '../components/GoldRecordForm';
import { goldRecordApi } from '../services/api';
import type { GoldRecord } from '../types/GoldRecord';
import styled from 'styled-components';

const { Content } = Layout;
const { Title, Text } = Typography;

const StyledCard = styled(Card)`
  background: #2a2a2a;
  color: white;
  border: none;
  border-radius: 16px;
  .ant-card-head-title {
    color: white;
  }
`;

const SummaryCard = styled(Card)`
  background: #2a2a2a;
  color: white;
  border: none;
  border-radius: 16px;
  margin-bottom: 16px;
  .ant-card-body {
    padding: 24px;
  }
`;

const GoldAmount = styled(Title)`
  color: #ffd700 !important;
  text-align: center;
  font-size: 48px !important;
  margin: 16px 0 !important;
`;

const StyledList = styled(List)`
  .ant-list-item {
    background: #3a3a3a;
    margin: 8px 0;
    padding: 16px;
    border-radius: 8px;
    border: none;
  }
`;

const PriceTag = styled.div`
  background: #ffd700;
  color: black;
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: bold;
  margin-bottom: 8px;
`;

const ActionButton = styled(Button)`
  background: #3a3a3a;
  color: white;
  border: none;
  &:hover {
    background: #4a4a4a;
    color: white;
  }
`;

const GoldRecordPage: React.FC = () => {
    const [records, setRecords] = useState<GoldRecord[]>([]);
    const [loading, setLoading] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);
    const [editingRecord, setEditingRecord] = useState<GoldRecord | undefined>();

    // 计算总计数据
    const totalWeight = records.reduce((sum, record) => sum + Number(record.weight), 0);
    const totalCost = records.reduce((sum, record) => sum + Number(record.totalPrice), 0);
    const currentGoldPrice = 742.99; // 这里应该从API获取实时金价
    const estimatedValue = totalWeight * currentGoldPrice;
    const estimatedProfit = estimatedValue - totalCost;

    const fetchRecords = async () => {
        try {
            setLoading(true);
            const data = await goldRecordApi.getAllRecords();
            setRecords(data);
        } catch (error) {
            message.error('获取记录失败');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRecords();
    }, []);

    const handleCreate = async (record: GoldRecord, photo?: File) => {
        try {
            await goldRecordApi.createRecord(record, photo);
            setModalVisible(false);
            fetchRecords();
        } catch (error) {
            throw error;
        }
    };

    const handleUpdate = async (record: GoldRecord, photo?: File) => {
        if (!editingRecord?.id) return;
        try {
            await goldRecordApi.updateRecord(editingRecord.id, record, photo);
            setModalVisible(false);
            setEditingRecord(undefined);
            fetchRecords();
        } catch (error) {
            throw error;
        }
    };

    const handleDelete = async (id: number) => {
        try {
            await goldRecordApi.deleteRecord(id);
            message.success('删除成功');
            fetchRecords();
        } catch (error) {
            message.error('删除失败');
            console.error(error);
        }
    };

    const calculateProfit = (record: GoldRecord) => {
        const currentValue = Number(record.weight) * currentGoldPrice;
        return currentValue - Number(record.totalPrice);
    };

    return (
        <Layout style={{ minHeight: '100vh', background: '#1a1a1a', padding: '16px' }}>
            <Content>
                <SummaryCard>
                    <Title level={5} style={{ color: 'white', textAlign: 'center', margin: 0 }}>
                        总重量（克）
                    </Title>
                    <GoldAmount>{totalWeight.toFixed(2)}</GoldAmount>
                    <Space style={{ width: '100%', justifyContent: 'space-between' }}>
                        <div>
                            <Text style={{ color: '#999' }}>购入总价（元）</Text>
                            <br />
                            <Text style={{ color: 'white' }}>{totalCost.toFixed(2)}</Text>
                        </div>
                        <div>
                            <Text style={{ color: '#999' }}>预估价值（元）</Text>
                            <br />
                            <Text style={{ color: 'white' }}>{estimatedValue.toFixed(2)}</Text>
                        </div>
                        <div>
                            <Text style={{ color: '#999' }}>预估收益（元）</Text>
                            <br />
                            <Text style={{ color: estimatedProfit >= 0 ? '#ff4d4f' : '#52c41a' }}>
                                {estimatedProfit.toFixed(2)}
                            </Text>
                        </div>
                    </Space>
                </SummaryCard>

                <Space style={{ marginBottom: 16, width: '100%', justifyContent: 'space-between' }}>
                    <Space>
                        <ActionButton icon={<FilterOutlined />}>筛选</ActionButton>
                        <ActionButton icon={<SortDescendingOutlined />}>排序</ActionButton>
                    </Space>
                    <Button
                        type="primary"
                        style={{ background: '#ffd700', borderColor: '#ffd700' }}
                        icon={<PlusOutlined />}
                        onClick={() => {
                            setEditingRecord(undefined);
                            setModalVisible(true);
                        }}
                    >
                        添加黄金
                    </Button>
                </Space>

                <StyledList
                    loading={loading}
                    dataSource={records}
                    renderItem={(record) => {
                        const profit = calculateProfit(record);
                        const pricePerGram = Number(record.totalPrice) / Number(record.weight);
                        
                        return (
                            <List.Item
                                onClick={() => handleDelete(record.id!)}
                                style={{ cursor: 'pointer' }}
                            >
                                <div style={{ width: '100%' }}>
                                    <PriceTag>{pricePerGram.toFixed(2)}元/克</PriceTag>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                        <div>
                                            <Text style={{ color: 'white', fontSize: '16px' }}>
                                                {record.purchaseChannel || '未知'}
                                            </Text>
                                            <br />
                                            <Text style={{ color: '#ffd700' }}>
                                                {record.weight}克 {record.totalPrice}元
                                            </Text>
                                        </div>
                                        <div style={{ textAlign: 'right' }}>
                                            <Text style={{ color: profit >= 0 ? '#ff4d4f' : '#52c41a' }}>
                                                收益：{profit >= 0 ? '+' : ''}{profit.toFixed(2)}
                                            </Text>
                                            <br />
                                            <Text style={{ color: '#999' }}>{record.purchaseDate}</Text>
                                        </div>
                                    </div>
                                </div>
                            </List.Item>
                        );
                    }}
                />

                <Modal
                    title={editingRecord ? '编辑记录' : '新增记录'}
                    open={modalVisible}
                    onCancel={() => {
                        setModalVisible(false);
                        setEditingRecord(undefined);
                    }}
                    footer={null}
                    destroyOnClose
                >
                    <GoldRecordForm
                        initialValues={editingRecord}
                        onSubmit={editingRecord ? handleUpdate : handleCreate}
                    />
                </Modal>
            </Content>
        </Layout>
    );
};

export default GoldRecordPage; 