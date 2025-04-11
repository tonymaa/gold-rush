import React, { useState, useEffect } from 'react';
import { Layout, Card, Button, Modal, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import GoldRecordForm from '../components/GoldRecordForm';
import GoldRecordList from '../components/GoldRecordList';
import { goldRecordApi } from '../services/api';
import type { GoldRecord } from '../types/GoldRecord';

const { Content } = Layout;

const GoldRecordPage: React.FC = () => {
    const [records, setRecords] = useState<GoldRecord[]>([]);
    const [loading, setLoading] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);
    const [editingRecord, setEditingRecord] = useState<GoldRecord | undefined>();

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

    const handleEdit = (record: GoldRecord) => {
        setEditingRecord(record);
        setModalVisible(true);
    };

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Content style={{ padding: '24px' }}>
                <Card
                    title="黄金攒金记录"
                    extra={
                        <Button
                            type="primary"
                            icon={<PlusOutlined />}
                            onClick={() => {
                                setEditingRecord(undefined);
                                setModalVisible(true);
                            }}
                        >
                            新增记录
                        </Button>
                    }
                >
                    <GoldRecordList
                        records={records}
                        onEdit={handleEdit}
                        onDelete={handleDelete}
                        loading={loading}
                    />
                </Card>

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