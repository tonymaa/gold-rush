import React, { useState, useEffect } from 'react';
import {
    Layout,
    Card,
    Button,
    Modal,
    message,
    Typography,
    Space,
    List,
    Statistic,
    StatisticProps,
    Tooltip,
    Menu, Dropdown
} from 'antd';
import { PlusOutlined, FilterOutlined, SortDescendingOutlined,MenuOutlined, DeleteOutlined, EditOutlined, ImportOutlined, ExportOutlined } from '@ant-design/icons';
import GoldRecordForm from '../components/GoldRecordForm';
import { goldRecordApi } from '../services/api';
import type { GoldRecord } from '../types/GoldRecord';
import styled from 'styled-components';
import CountUp from 'react-countup';
import moment from "moment/moment";

const formatter: StatisticProps['formatter'] = (value) => (
    <CountUp end={value as number} separator="," decimals={2} duration={0.3}/>
);
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
` as typeof List;

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

const IconButton = styled.span`
  color: #999;
  cursor: pointer;
  padding: 4px;
  transition: all 0.3s;
  
  &:hover {
    color: #ffd700;
  }
`;

const StyledStatistic = styled(Statistic)`
    .ant-statistic-content-value {
        color: white !important;
        font-size: 16px !important;
    }
    .ant-statistic-title {
        color: #999 !important;
        margin-bottom: 4px !important;
    }
`;

const ProfitStatistic = styled(Statistic)<{ $isPositive: boolean }>`
    .ant-statistic-content-value, .ant-statistic-content-suffix {
        color: ${props => props.$isPositive ? '#ff4d4f' : '#52c41a'} !important;
        font-size: 16px !important;
    }
    .ant-statistic-title {
        color: #999 !important;
        margin-bottom: 4px !important;
    }
`;

const GoldPriceStatistic = styled(Statistic)`
    .ant-statistic-content {
        color: #ffd700 !important;
        font-size: 16px !important;
        text-align: center;
    }
    .ant-statistic-title {
        color: #999 !important;
        text-align: center;
        margin-bottom: 4px !important;
    }
`;

const GoldRecordPage: React.FC = () => {
    const [records, setRecords] = useState<GoldRecord[]>([]);
    const [loading, setLoading] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);
    const [editingRecord, setEditingRecord] = useState<GoldRecord | undefined>();
    const [currentGoldPrice, setCurrentGoldPrice] = useState<number>(0);
    const [currentGoldPriceBid, setCurrentGoldPriceBid] = useState<number>(0);
    const [todayPrices, setTodayPrices] = useState<Array<any>>([]);
    const [sortOrder, setSortOrder] = useState<'asc' | 'desc'>('desc'); // 添加排序状态

    // 计算总计数据
    const totalWeight = records.reduce((sum, record) => sum + Number(record.weight), 0);
    const totalCost = records.reduce((sum, record) => sum + Number(record.totalPrice), 0);
    const estimatedValue = totalWeight * currentGoldPrice;
    const estimatedProfit = estimatedValue - totalCost;

    const fetchCurrentPrice = async () => {
        try {
            const price = await goldRecordApi.getCurrentGoldPrice();
            setCurrentGoldPrice(price.data.sell);
            setCurrentGoldPriceBid(price.data.bid);
        } catch (error) {
            message.error('获取金价失败');
            console.error(error);
        }
    };

    const fetchTodayPrices = async () => {
        try {
            const data = await goldRecordApi.getTodayPrices();
            console.log(data)
            const res = (data.data as any[]).map((item: any) => {
                return {
                    data: (item.updateDate as string).split(' ')[1],
                    value: item.sell
                }
            })
            setTodayPrices(res)

        } catch (error) {
            message.error('获取今日价格走向失败');
            console.error(error);
        }
    }

    const fetchRecords = async () => {
        try {
            setLoading(true);
            const data = await goldRecordApi.getAllRecords();
            // 默认先按时间排序
            const sortedData = [...data].sort((a, b) => {
                return moment(b.purchaseDate).valueOf() - moment(a.purchaseDate).valueOf();
            });
            setRecords(sortedData);
        } catch (error) {
            message.error('获取记录失败');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCurrentPrice();
        fetchRecords();
        // fetchTodayPrices();

        // 每分钟更新一次金价
        const priceInterval = setInterval(fetchCurrentPrice, 10000);
        // const price2Interval = setInterval(fetchTodayPrices, 10000);
        return () => {
            clearInterval(priceInterval)
            // clearInterval(price2Interval)
        };
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

    const handleExport = () => {
        try {
            const csvContent = records.map(record => {
                console.log('record =>', record);
                return [
                    record.purchaseChannel || '未知',
                    record.weight,
                    record.totalPrice,
                    moment(record.purchaseDate).format('YYYY-MM-DD HH:mm:ss')
                ].join(',');
            });

            const header = ['购买渠道', '重量(克)', '总价(元)', '购买时间'].join(',');
            const csv = [header, ...csvContent].join('\n');

            const blob = new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8;' });
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = `黄金记录_${moment().format('YYYY-MM-DD')}.csv`;
            link.click();
            URL.revokeObjectURL(link.href);
            message.success('导出成功');
        } catch (error) {
            message.error('导出失败');
            console.error(error);
        }
    };

    const handleImport = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        console.log('333 =>', );
        if (!file) return;

        console.log('666 =>', );
        const reader = new FileReader();
        reader.onload = async (e) => {
            try {
                const text = e.target?.result as string;
                const lines = text.split('\n');
                lines.shift(); // 移除表头

                for (const line of lines) {
                    if (!line.trim()) continue;
                    const [purchaseChannel, weight, totalPrice, purchaseDate] = line.split(',');

                    const record: GoldRecord = {
                        purchaseChannel: purchaseChannel.trim(),
                        weight: Number(weight),
                        totalPrice: Number(totalPrice),
                        purchaseDate: moment(purchaseDate.trim()).toString(),
                        isSummary: true
                    };

                    await goldRecordApi.createRecord(record);
                }

                message.success('导入成功');
                fetchRecords();
            } catch (error) {
                message.error('导入失败');
                console.error(error);
            }
        };
        reader.readAsText(file);
    };


    const handleSort = () => {
        const newOrder = sortOrder === 'desc' ? 'asc' : 'desc';
        setSortOrder(newOrder);
        const sortedRecords = [...records].sort((a, b) => {
            const timeA = moment(a.purchaseDate).valueOf();
            const timeB = moment(b.purchaseDate).valueOf();
            return newOrder === 'asc' ? timeA - timeB : timeB - timeA;
        });
        setRecords(sortedRecords);
    };


    return (
        <Layout style={{ minHeight: '100vh', background: '#1a1a1a', padding: '16px' }}>
            <Content>
                <SummaryCard>
                    <Title level={5} style={{ color: 'white', textAlign: 'center', margin: 0 }}>
                        总重量（克）
                    </Title>
                    <GoldAmount>{totalWeight?.toFixed(2)}</GoldAmount>
                    <Space style={{ width: '100%', justifyContent: 'space-between' }}>
                        <StyledStatistic
                            title="购入总价（元）"
                            value={totalCost}
                            precision={2}
                            formatter={formatter}
                        />
                        <StyledStatistic
                            title="预估价值（元）"
                            value={estimatedValue}
                            precision={2}
                            formatter={formatter}
                        />
                        <div>
                            <Tooltip
                                rootClassName={'tooltip-gold-record-profit'}
                                color={estimatedProfit >= 0 ? '#ff4d4f' : '#52c41a'}
                                title={`${isNaN((estimatedProfit / totalCost) * 100) ? 0 : ((estimatedProfit / totalCost) * 100)?.toFixed(2)}%`}
                            >
                                <span>
                                    <ProfitStatistic
                                        rootClassName={'profit-statistic'}
                                        title="预估收益（元）"
                                        value={estimatedProfit}
                                        precision={2}
                                        $isPositive={estimatedProfit >= 0}
                                        formatter={formatter}
                                        suffix={`/ ${isNaN((estimatedProfit / totalCost) * 100) ? 0 : ((estimatedProfit / totalCost) * 100)?.toFixed(2)}%`}
                                    />
                                </span>
                            </Tooltip>
                            {/*<ProfitStatistic
                                value={((estimatedProfit / totalCost) * 100)?.toFixed(2)}
                                precision={2}
                                $isPositive={estimatedProfit >= 0}
                                formatter={formatter}
                                suffix={'%'}
                            />*/}
                        </div>
                    </Space>
                    <div style={{ display: 'flex', gap: '16px', justifyContent: 'center', }}>
                        <div style={{ marginTop: 16 }}>
                            <GoldPriceStatistic
                              title="当前买入价"
                              value={currentGoldPriceBid}
                              precision={2}
                              suffix="元/克"
                              formatter={formatter}
                            />
                        </div>
                        <div style={{ marginTop: 16 }}>
                            <GoldPriceStatistic
                              title="当前金价"
                              value={currentGoldPrice}
                              precision={2}
                              suffix="元/克"
                              formatter={formatter}
                            />
                        </div>
                        <div style={{ marginTop: 16 }}>
                            <GoldPriceStatistic
                              title="当前成本价"
                              value={totalCost / totalWeight}
                              precision={2}
                              suffix="元/克"
                              formatter={formatter}
                            />
                        </div>
                    </div>
                </SummaryCard>

                <Space style={{ marginBottom: 16, width: '100%', justifyContent: 'space-between' }}>
                    <Space>
                        <ActionButton icon={<FilterOutlined/>}>筛选</ActionButton>
                        <ActionButton
                            icon={<SortDescendingOutlined/>}
                            onClick={handleSort}
                        >
                            {sortOrder === 'desc' ? '时间降序' : '时间升序'}
                        </ActionButton>
                        <Dropdown menu={{
                            items: [
                                {
                                    key: '1',
                                    label: (
                                        <>
                                            <ActionButton icon={<ImportOutlined />} onClick={() => document.getElementById('fileInput')?.click()}>
                                                导入
                                            </ActionButton>
                                            <input
                                                id="fileInput"
                                                type="file"
                                                accept=".csv"
                                                style={{ display: 'none' }}
                                                onChange={handleImport}
                                            />
                                        </>
                                    )
                                },
                                {
                                    key: '2',
                                    label: (
                                        <ActionButton icon={<ExportOutlined />} onClick={handleExport}>
                                            导出
                                        </ActionButton>
                                    )
                                }
                            ]
                        }}
                        >
                            <ActionButton>
                                <MenuOutlined/>
                            </ActionButton>
                        </Dropdown>
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
                    renderItem={(record: GoldRecord) => {
                        const profit = calculateProfit(record);
                        const pricePerGram = Number(record.totalPrice) / Number(record.weight);

                        return (
                            <List.Item
                                style={{ cursor: 'pointer' }}
                            >
                                <div style={{ width: '100%' }}>
                                    <div style={{display: 'flex'}}>
                                        <div>
                                            <PriceTag>{pricePerGram.toFixed(2)}元/克</PriceTag>
                                        </div>
                                        <div style={{flex: 1, textAlign: 'right'}}>
                                            <div style={{display: 'flex', gap: '22px'}}>
                                                <div style={{flex: 1}}/>
                                                <IconButton onClick={() => {
                                                    setEditingRecord(record);
                                                    setModalVisible(true);
                                                }}>
                                                    <EditOutlined />
                                                </IconButton>
                                                <IconButton onClick={() => {
                                                    Modal.confirm({
                                                        title: '删除？',
                                                        content: '确认删除吗？',
                                                        onOk: () => {
                                                            handleDelete(record.id!)
                                                        }
                                                    })
                                                }}>
                                                    <DeleteOutlined />
                                                </IconButton>
                                            </div>
                                        </div>
                                    </div>
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
                                                <div style={{display: 'flex'}}>
                                                    <div>收益：</div>
                                                    <div style={{display: 'flex', flexDirection: 'column'}}>
                                                        <div>{profit >= 0 ? '+' : ''}{profit?.toFixed(2)}</div>
                                                        <div>{profit >= 0 ? '+' : ''}{((profit / record.totalPrice) * 100)?.toFixed(2)}%</div>
                                                    </div>
                                                </div>
                                            </Text>
                                            <br />
                                            <Text style={{ color: '#999' }}>{moment(record.purchaseDate).format('YYYY-MM-DD HH:mm:ss')}</Text>
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
