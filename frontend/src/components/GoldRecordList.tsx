import React from 'react';
import { Table, Space, Button, Popconfirm, Image } from 'antd';
import type { GoldRecord } from '../types/GoldRecord';
import moment from 'moment';

interface Props {
    records: GoldRecord[];
    onEdit: (record: GoldRecord) => void;
    onDelete: (id: number) => Promise<void>;
    loading?: boolean;
}

const GoldRecordList: React.FC<Props> = ({ records, onEdit, onDelete, loading }) => {
    const columns = [
        {
            title: '重量（克）',
            dataIndex: 'weight',
            key: 'weight',
            sorter: (a: GoldRecord, b: GoldRecord) => a.weight - b.weight,
        },
        {
            title: '成交总价',
            dataIndex: 'totalPrice',
            key: 'totalPrice',
            sorter: (a: GoldRecord, b: GoldRecord) => a.totalPrice - b.totalPrice,
        },
        {
            title: '购买渠道',
            dataIndex: 'purchaseChannel',
            key: 'purchaseChannel',
        },
        {
            title: '备注',
            dataIndex: 'remarks',
            key: 'remarks',
            ellipsis: true,
        },
        {
            title: '购买日期',
            dataIndex: 'purchaseDate',
            key: 'purchaseDate',
            render: (date: string) => moment(date).format('YYYY-MM-DD HH:mm:ss'),
            sorter: (a: GoldRecord, b: GoldRecord) => 
                moment(a.purchaseDate).valueOf() - moment(b.purchaseDate).valueOf(),
        },
        {
            title: '照片',
            dataIndex: 'photoUrl',
            key: 'photoUrl',
            render: (url: string) => url ? (
                <Image
                    src={`http://localhost:8080/uploads/${url}`}
                    alt="Gold"
                    width={50}
                    height={50}
                    style={{ objectFit: 'cover' }}
                />
            ) : null,
        },
/*        {
            title: '模式',
            dataIndex: 'isSummary',
            key: 'isSummary',
            render: (isSummary: boolean) => isSummary ? '汇总模式' : '明细模式',
            filters: [
                { text: '汇总模式', value: true },
                { text: '明细模式', value: false },
            ],
            onFilter: (value: boolean, record: GoldRecord) => record.isSummary === value,
        },*/
        {
            title: '操作',
            key: 'action',
            render: (_: any, record: GoldRecord) => (
                <Space size="middle">
                    <Button type="link" onClick={() => onEdit(record)}>
                        编辑
                    </Button>
                    <Popconfirm
                        title="确定要删除这条记录吗？"
                        onConfirm={() => record.id && onDelete(record.id)}
                        okText="确定"
                        cancelText="取消"
                    >
                        <Button type="link" danger>
                            删除
                        </Button>
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    return (
        <Table
            columns={columns}
            dataSource={records}
            rowKey="id"
            loading={loading}
            pagination={{
                showSizeChanger: true,
                showTotal: (total) => `共 ${total} 条记录`,
            }}
        />
    );
};

export default GoldRecordList; 